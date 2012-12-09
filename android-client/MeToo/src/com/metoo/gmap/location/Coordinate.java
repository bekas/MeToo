package com.metoo.gmap.location;

public class Coordinate {
	
	public Coordinate(double latitude, double longitude)
	{
		Latitude = latitude;
		Longitude = longitude;
	}
	public Coordinate()
	{
		Latitude = 0;
		Longitude = 0;
	}
	
	public double Latitude;
	public double Longitude;
}
