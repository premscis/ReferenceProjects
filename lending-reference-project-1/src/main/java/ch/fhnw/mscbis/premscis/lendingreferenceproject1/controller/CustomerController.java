/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.mscbis.premscis.lendingreferenceproject1.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;

import ch.fhnw.mscbis.premscis.lendingreferenceproject1.domain.Customer;
import ch.fhnw.mscbis.premscis.lendingreferenceproject1.ejb.CustomerEJB;

/**
 *
 * @author andreas.martin
 */
@ManagedBean
@SessionScoped //we need a session!
public class CustomerController {

    public CustomerController() {
    }
    @EJB
    private CustomerEJB customerEJB;
    private Customer customer;
    private List<Customer> customerList;
    private HtmlDataTable customerHtmlDataTable;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomerList() {
        customerList = customerEJB.findCustomers();
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }
    
    public String prepareCreateCustomer()
    {
        this.customer = new Customer();
        return "newCustomerCase";
    }

    public String doCreateCustomer() {
        customer = customerEJB.createCustomer(customer);
        customerList = customerEJB.findCustomers();
        return "listCustomersCase";
    }
    
    public HtmlDataTable getCustomerHtmlDataTable() {
        return customerHtmlDataTable;
    }

    public void setCustomerHtmlDataTable(HtmlDataTable customerHtmlDataTable) {
        this.customerHtmlDataTable = customerHtmlDataTable;
    }
    
    public String prepareEditCustomer()
    {
        this.customer = (Customer) this.customerHtmlDataTable.getRowData();
        return "editCustomerCase";
    }
    
    public String doEditCustomer()
    {
        customer=customerEJB.updateCustomer(customer);
        customerList = customerEJB.findCustomers();
        return "listCustomersCase";
    }
    
    public String doDeleteCustomer()
    {
        this.customer = (Customer) this.customerHtmlDataTable.getRowData();
        customerEJB.deleteCustomer(customer);
        customer=new Customer();
        customerList = customerEJB.findCustomers();
        return "listCustomersCase";
    }
    
}