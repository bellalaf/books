package com.bellalaf.books

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
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
}

interface BookRepository : CrudRepository<Book, String>

@Table("BOOKS")
data class Book(@Id var id: String?, val text: String)