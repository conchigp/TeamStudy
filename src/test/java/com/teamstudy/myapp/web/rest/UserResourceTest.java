package com.teamstudy.myapp.web.rest;


public class UserResourceTest {

	public static void main(String[] args){
		String name = "Apuntes.rar";
		String format, name2;
		int i;
		i = name.lastIndexOf(".");
		System.out.println(i);
		format = name.substring(i+1);
		System.out.println(format);
		name2 = name.substring(0, i);
		System.out.println(name2);
	}
}
