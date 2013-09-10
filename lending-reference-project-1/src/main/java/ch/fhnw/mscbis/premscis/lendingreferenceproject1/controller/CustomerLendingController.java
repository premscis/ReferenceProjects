/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.mscbis.premscis.lendingreferenceproject1.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;

import ch.fhnw.mscbis.premscis.lendingreferenceproject1.domain.Book;
import ch.fhnw.mscbis.premscis.lendingreferenceproject1.domain.BookLending;
import ch.fhnw.mscbis.premscis.lendingreferenceproject1.domain.Customer;
import ch.fhnw.mscbis.premscis.lendingreferenceproject1.ejb.BookLendingEJB;

/**
 *
 * @author andreas.martin
 */
@ManagedBean
@SessionScoped
public class CustomerLendingController{

    @EJB
    private BookLendingEJB bookLendingEJB;
    @ManagedProperty(value="#{customerController}")
    private CustomerController customerController;
    private List<BookLending> bookLendingList;
    private HtmlDataTable bookLendingHtmlDataTable;
    private BookLending bookLending;
    private List<Book> notLendedBookList;
    private HtmlDataTable notLendedBookHtmlDataTable;
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public HtmlDataTable getNotLendedBookHtmlDataTable() {
        return notLendedBookHtmlDataTable;
    }

    public void setNotLendedBookHtmlDataTable(HtmlDataTable notLendedBookHtmlDataTable) {
        this.notLendedBookHtmlDataTable = notLendedBookHtmlDataTable;
    }

    public List<Book> getNotLendedBookList() {
        this.notLendedBookList = bookLendingEJB.showAllNotLendedBooks();
        return notLendedBookList;
    }

    public void setNotLendedBookList(List<Book> notLendedBookList) {
        this.notLendedBookList = notLendedBookList;
    }

    public HtmlDataTable getBookLendingHtmlDataTable() {
        return bookLendingHtmlDataTable;
    }

    public void setBookLendingHtmlDataTable(HtmlDataTable bookLendingHtmlDataTable) {
        this.bookLendingHtmlDataTable = bookLendingHtmlDataTable;
    }

    public List<BookLending> getBookLendingList() {
        this.bookLendingList = bookLendingEJB.showAllLendings(customerController.getCustomer());
        return bookLendingList;
    }

    public void setBookLendingList(List<BookLending> bookLendingList) {
        this.bookLendingList = bookLendingList;
    }

    public CustomerController getCustomerController() {
        return customerController;
    }

    public void setCustomerController(CustomerController customerController) {
        this.customerController = customerController;
    }
    
    public String doEndLending()
    {
        this.bookLending = (BookLending) this.bookLendingHtmlDataTable.getRowData();
        bookLendingEJB.returnBook(bookLending.getBook(), customerController.getCustomer());
        getBookLendingList();
        getNotLendedBookList();
        return "customerLendingCase";
    }
    
    public String doLending()
    {
        this.book = (Book) this.notLendedBookHtmlDataTable.getRowData();
        bookLendingEJB.lendBook(book, customerController.getCustomer());
        getBookLendingList();
        getNotLendedBookList();
        return "customerLendingCase";
    }
    
    public String prepareLending()
    {
        customerController.setCustomer((Customer) customerController.getCustomerHtmlDataTable().getRowData());
        return "customerLendingCase";
    }
    
    public String doDeleteLending()
    {
        this.bookLending = (BookLending) this.bookLendingHtmlDataTable.getRowData();
        bookLendingEJB.deleteBookLending(bookLending);
        getBookLendingList();
        getNotLendedBookList();
        return "customerLendingCase";
    }
}
