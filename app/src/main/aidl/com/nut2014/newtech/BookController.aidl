package com.nut2014.newtech;
import com.nut2014.newtech.Book;

import java.util.List;

interface BookController {

    List<Book> getBookList();

    void addBookInOut(inout Book book);

}
