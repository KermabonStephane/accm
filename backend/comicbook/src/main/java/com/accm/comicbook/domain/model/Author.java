package com.accm.comicbook.domain.model;

import java.util.UUID;

public record Author(UUID id, String firstname, String lastname, String middlename) {
}
