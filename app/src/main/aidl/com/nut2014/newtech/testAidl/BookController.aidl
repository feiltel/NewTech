package com.nut2014.newtech.testAidl;
import com.nut2014.newtech.testAidl.Book;

import java.util.List;

interface BookController {

    List<Book> getBookList();

    void addBookInOut(inout Book book);

}
