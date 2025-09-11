package com.foodwaste.model;

public class Delivery {
    private int id;
    private int donationid;
    private int personid;
    private int recipientid;
    private String status;
    private String pickuptime;
    private String deliverytime;
    private String confirmationtype;
    private String confirmationdata;

    public Delivery() {}

    public Delivery(int id, int donationid, int personid, int recipientid, String status, String pickuptime, String deliverytime, String confirmationtype, String confirmationdata) 
    {
        this.id = id;
        this.donationid = donationid;
        this.personid = personid;
        this.recipientid = recipientid;
        this.status = status;
        this.pickuptime = pickuptime;
        this.deliverytime = deliverytime;
        this.confirmationtype = confirmationtype;
        this.confirmationdata = confirmationdata;
    }

    
    
  
    public int getId()
    {
    	return id;
    }
    public void setId(int id)
    {
    	this.id=id;
    }
    
    
    public int getDonationid()
    {
    	return donationid;
    }
    public void setDonationid(int donationid)
    {
    	this.donationid=donationid;
    }
    
    
    public int getPersonid()
    {
    	return personid;
    }
    public void setPersonid(int personid)
    {
    	this.personid=personid;
    }
    
    
    public int getrecipientid()
    {
    	return recipientid;
    }
    public void setRecipientid(int recipientid)
    {
    	this.recipientid=recipientid;
    }
    
    
    public String getStatus()
    {
    	return status;
    }
    public void setStatus(String status)
    {
    	this.status=status;
    }
    
    
    public String getPickuptime()
    {
    	return pickuptime;
    }
    public void setPickuptime(String pickuptime)
    {
    	this.pickuptime=pickuptime;
    }
    
    
    public String getDelivertime()
    {
    	return deliverytime;
    }
    public void setDeliverytime(String deliverytime)
    {
    	this.deliverytime=deliverytime;
    }
    
    
    public String getConfirmationtype()
    {
    	return confirmationtype;
    }
    public void setConfirmationtype(String confirmationtype)
    {
    	this.confirmationtype=confirmationtype;
    }
    
    
    public String getConfirmationdata()
    {
    	return confirmationdata;
    }
    public void setConfirmationdata(String confirmationdata)
    {
    	this.confirmationdata=confirmationdata;
    }
}
