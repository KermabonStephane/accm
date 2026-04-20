package com.accm.comicbook.infrastructure.web

import com.accm.comicbook.domain.model.Series
import com.accm.comicbook.domain.model.SeriesStatus
import com.accm.comicbook.infrastructure.web.dto.SeriesDto
import spock.lang.Specification
import spock.lang.Subject

class SeriesWebMapperSpec extends Specification {

    @Subject
    def mapper = new SeriesWebMapperImpl()

    def "toDto maps all fields"() {
        given:
        def parentId = UUID.randomUUID()
        def series = Series.builder().id(UUID.randomUUID()).name("Watchmen").status(SeriesStatus.ONGOING).parentId(parentId).build()

        when:
        def dto = mapper.toDto(series)

        then:
        dto.id() == series.id()
        dto.name() == "Watchmen"
        dto.status() == SeriesStatus.ONGOING
        dto.parentId() == parentId
    }

    def "toDto maps series without parentId"() {
        given:
        def series = Series.builder().id(UUID.randomUUID()).name("V for Vendetta").status(SeriesStatus.COMPLETED).build()

        when:
        def dto = mapper.toDto(series)

        then:
        dto.id() == series.id()
        dto.name() == "V for Vendetta"
        dto.status() == SeriesStatus.COMPLETED
        dto.parentId() == null
    }

    def "toDomain maps fields and ignores id"() {
        given:
        def dto = new SeriesDto(UUID.randomUUID(), "Watchmen", SeriesStatus.ONGOING, null)

        when:
        def series = mapper.toDomain(dto)

        then:
        series.id() == null
        series.name() == "Watchmen"
        series.status() == SeriesStatus.ONGOING
        series.parentId() == null
    }

    def "toDomain maps dto with parentId"() {
        given:
        def parentId = UUID.randomUUID()
        def dto = new SeriesDto(UUID.randomUUID(), "Sub-series", SeriesStatus.ONGOING, parentId)

        when:
        def series = mapper.toDomain(dto)

        then:
        series.id() == null
        series.name() == "Sub-series"
        series.parentId() == parentId
    }
}
