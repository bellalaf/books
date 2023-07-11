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

    @GetMapping("/import")
    fun import() {
        val input = File ("goodreads.csv").inputStream()
        var books = service.import(input)
        service.saveMultiple(books)
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
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map {
                val (title, id, author) = it.split(',', ignoreCase = false, limit = 3)
                Book(text = title.trim().removeSurrounding("\""), id = null)
            }.toList()
    }

    fun saveMultiple(books: List<Book>) {
        books.forEach { save(it) }
    }
}

interface BookRepository : CrudRepository<Book, String>

@Table("BOOKS")
data class Book(@Id var id: String?, val text: String)