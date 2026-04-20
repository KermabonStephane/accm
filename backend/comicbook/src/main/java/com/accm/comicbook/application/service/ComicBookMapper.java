package com.accm.comicbook.application.service;

import com.accm.comicbook.domain.model.Author;
import com.accm.comicbook.domain.model.AuthorRole;
import com.accm.comicbook.domain.model.ComicBook;
import com.accm.comicbook.domain.model.ComicBookAuthor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ComicBookMapper {

    @Mapping(target = "id", source = "author.id")
    @Mapping(target = "firstname", source = "author.firstname")
    @Mapping(target = "lastname", source = "author.lastname")
    @Mapping(target = "middleName", source = "author.middlename")
    @Mapping(target = "role", source = "role")
    ComicBookAuthor toComicBookAuthor(Author author, AuthorRole role);

    @Mapping(target = "id", source = "existing.id")
    @Mapping(target = "status", source = "existing.status")
    @Mapping(target = "authors", source = "existing.authors")
    @Mapping(target = "title", source = "update.title")
    @Mapping(target = "isbn", source = "update.isbn")
    @Mapping(target = "date", source = "update.date")
    @Mapping(target = "seriesId", source = "update.seriesId")
    @Mapping(target = "issueNumber", source = "update.issueNumber")
    @Mapping(target = "volumeNumber", source = "update.volumeNumber")
    ComicBook applyUpdate(ComicBook existing, ComicBook update);
}
