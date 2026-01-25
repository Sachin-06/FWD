package com.foodwaste.model;

public class Donation {
	public int id;
	public int donorid;
	public String foodtype;
	public String quantity;
	public String pickup;
	public String status;
	
	public Donation() {}
	
	public Donation(int id,int donorid,String foodtype,String quantity,String pickup,String status) {
		this.id=id;
		this.donorid=donorid;
		this.foodtype=foodtype;
		this.quantity=quantity;
		this.pickup=pickup;
		this.status=status;
	}
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	
	
	public int getDonorid()
	{
		return donorid;
	}
	public void setDonorid(int donorid)
	{
		this.donorid=donorid;
	}
	
	
	public String getFoodtype()
	{
		return foodtype;
	}
	public void setFoodtype(String foodtype)
	{
		this.foodtype=foodtype;
	}
	
	
	public String getQuantity()
	{
		return quantity;
	}
	public void setQuantity(String quantity)
	{
		this.quantity=quantity;
	}
	
	
	public String getPickup()
	{
		return pickup;
	}
	public void setPickup(String pickup)
	{
		this.pickup=pickup;
	}
	
	
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status=status;
	}
	

}
