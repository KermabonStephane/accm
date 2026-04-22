package com.accm.comicbook.domain.port.in;

import com.accm.comicbook.domain.model.Editor;

import java.util.List;

public interface ListEditorsUseCase {
    List<Editor> listEditors();
}
