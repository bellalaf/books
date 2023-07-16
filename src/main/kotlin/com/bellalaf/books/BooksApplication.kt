package com.bellalaf.books

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.InputStream
import java.util.*

@SpringBootApplication
class BooksApplication

fun main(args: Array<String>) {
    runApplication<BooksApplication>(*args)
}

@RestController
class MessageController(val service: BookService) {

    @GetMapping("/")
    fun index(): List<Book> = service.findBooks()

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): List<Book> {
        return service.findBookById(id)
    }

    @PostMapping("/")
    fun post(@RequestBody book: Book) {
        service.save(book)
    }

    @DeleteMapping("/")
    fun delete() {
        service.deleteAll()
    }

    @GetMapping("/import-demo")
    fun import(): List<Book> {
        val input = File ("goodreads.csv").inputStream()
        var books = service.import(input)
        service.saveMultiple(books)
        return service.findBooks()
    }
}

@Service
class BookService(val db: BookRepository) {
    fun findBooks(): List<Book> = db.findAll().toList()

    fun save(book: Book) {
        db.save(book)
    }

    fun findBookById(id: String): List<Book> = db.findById(id).toList()

    fun deleteAll() {
        db.deleteAll()
    }

    fun <T : Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()

    fun import(input: InputStream): List<Book> {
        val reader = input.bufferedReader()

        //read first line - header only
        reader.readLine()

        //TODO: csv is being annoying - either need to clean the data up, or use a library to handle commas in the data
        //TODO: also save ISBN and ignore duplicate books
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val line = it.split(',', limit = 24)
                Book(title = cleanUp(line[Goodreads.TITLE.csv_position]),
                        author = cleanUp(line[Goodreads.AUTHOR.csv_position]),
                        rating = cleanUp(line[Goodreads.RATING.csv_position]),
                        num_pages = cleanUp(line[Goodreads.NUM_PAGES.csv_position]),
                        date_read = cleanUp(line[Goodreads.DATE_READ.csv_position]),
                        review = cleanUp(line[Goodreads.REVIEW.csv_position]),
                        shelves = cleanUp(line[Goodreads.BOOKSHELVES.csv_position])
                )
            }.toList()
    }



    fun saveMultiple(books: List<Book>) {
        books.forEach { save(it) }
    }

    fun cleanUp(str: String): String = str.trim().removeSurrounding("\"")
}

interface BookRepository : CrudRepository<Book, String>

@Table("BOOKS_2")
//=null so that we can default construct a value without explictly calling it null
data class Book(
        @Id var id: String? = null,
        val title: String,
        val author: String? = null,
        val rating: String? = null,
        val num_pages: String? = null,
        val date_read: String? = null,
        val review: String? = null,
        val shelves: String? = null,
        )

enum class Goodreads(val csv_position: Int) {
    TITLE(1),
    AUTHOR(2),
    RATING(7),
    NUM_PAGES(11),
    DATE_READ(14),
    REVIEW(19),
    BOOKSHELVES(16),
}