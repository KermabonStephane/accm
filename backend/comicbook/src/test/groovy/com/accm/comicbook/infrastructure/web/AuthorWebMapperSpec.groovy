package com.accm.comicbook.infrastructure.web

import com.accm.comicbook.domain.model.Author
import com.accm.comicbook.infrastructure.web.dto.AuthorDto
import spock.lang.Specification
import spock.lang.Subject

class AuthorWebMapperSpec extends Specification {

    @Subject
    def mapper = new AuthorWebMapperImpl()

    def "toDto maps all fields"() {
        given:
        def author = new Author(UUID.randomUUID(), "Alan", "Moore", "Oswald")

        when:
        def dto = mapper.toDto(author)

        then:
        dto.id() == author.id()
        dto.firstname() == "Alan"
        dto.lastname() == "Moore"
        dto.middlename() == "Oswald"
    }

    def "toDto maps author without middlename"() {
        given:
        def author = new Author(UUID.randomUUID(), "Dave", "Gibbons", null)

        when:
        def dto = mapper.toDto(author)

        then:
        dto.id() == author.id()
        dto.firstname() == "Dave"
        dto.lastname() == "Gibbons"
        dto.middlename() == null
    }

    def "toDomain maps fields and ignores id"() {
        given:
        def dto = new AuthorDto(UUID.randomUUID(), "Alan", "Moore", "Oswald")

        when:
        def author = mapper.toDomain(dto)

        then:
        author.id() == null
        author.firstname() == "Alan"
        author.lastname() == "Moore"
        author.middlename() == "Oswald"
    }
}
