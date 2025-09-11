package com.foodwaste.model;

public class Recipient {
    private int id;
    private String name;
    private String contactperson;
    private String phone;
    private String address;
    private String recipienttype;

    public Recipient() {}

    public Recipient(int id, String name, String contactperson, String phone, String address, String recipienttype)
    {
        this.id = id;
        this.name = name;
        this.contactperson = contactperson;
        this.phone = phone;
        this.address = address;
        this.recipienttype = recipienttype;
    }

   
    public int getId()
    {
    	return id;
    }
    public void setId(int id)
    {
    	this.id=id;
    }
    
    
    public String getName()
    {
    	return name;
    }
    public void getName(String name)
    {
    	this.name=name;
    }
    
    
    public String getContactperson()
    {
    	return contactperson;
    }
    public void getContactperson(String contactperson)
    {
    	this.contactperson=contactperson;
    }
    
    
    public String getPhone()
    {
    	return phone;
    }
    public void getPhone(String phone)
    {
    	this.phone=phone;
    }
    
    
    public String getAddress()
    {
    	return address;
    }
    public void getAddress(String address)
    {
    	this.address=address;
    }
    
    
    public String getRecipienttype()
    {
    	return recipienttype;
    }
    public void getRecipienttype(String recipienttype)
    {
    	this.recipienttype=recipienttype;
    }
}
