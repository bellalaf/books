package com.bellalaf.books

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*

@SpringBootApplication
class BooksApplication

fun main(args: Array<String>) {
    runApplication<BooksApplication>(*args)
}

@RestController
class MessageController(val service: MessageService) {

    @GetMapping("/")
    fun index(): List<Message> = service.findMessages()

    @GetMapping("/{id}")
    fun index(@PathVariable id: String): List<Message> {
        print("YEP")
        return service.findMessageById(id)
    }

    @PostMapping("/")
    fun post(@RequestBody message: Message) {
        service.save(message)
    }

    @DeleteMapping("/")
    fun delete() {
        service.deleteAll()
    }
}

@Service
class MessageService(val db: JdbcTemplate) {
    fun findMessages(): List<Message> = db.query("select * from messages") { response, _ ->
        Message(response.getString("id"), response.getString("text"))
    }

    fun save(message: Message) {
        val id = message.id ?: UUID.randomUUID().toString()
        db.update("insert into messages values ( ?, ? )",
            id, message.text)
    }

    fun findMessageById(id: String): List<Message> = db.query("select * from messages where id = $id") { response, _ ->
        Message(response.getString("id"), response.getString("text"))
    }

    fun deleteAll() {
        db.update("delete from messages")
    }
}

data class Message(val id: String?, val text: String)