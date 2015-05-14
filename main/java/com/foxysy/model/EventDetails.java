package com.foxysy.model;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class EventDetails {

	ArrayList<String> background_header_ar;
	ArrayList<String> background_footer_ar;
	ArrayList<String> mon_ar;
	ArrayList<String> tue_ar;
	ArrayList<String> wed_ar;
	ArrayList<String> thu_ar;
	ArrayList<String> fri_ar;
	ArrayList<String> sat_ar;
	ArrayList<String> sun_ar;

	Bitmap images_ar;

	String hostname, color_header_str, color_footer_str, title, content;

	public ArrayList<String> getBackground_header_ar() {
		return background_header_ar;
	}

	public void setBackground_header_ar(ArrayList<String> background_header_ar) {
		this.background_header_ar = background_header_ar;
	}

	public ArrayList<String> getBackground_footer_ar() {
		return background_footer_ar;
	}

	public void setBackground_footer_ar(ArrayList<String> background_footer_ar) {
		this.background_footer_ar = background_footer_ar;
	}

	public ArrayList<String> getMon_ar() {
		return mon_ar;
	}

	public void setMon_ar(ArrayList<String> mon_ar) {
		this.mon_ar = mon_ar;
	}

	public ArrayList<String> getTue_ar() {
		return tue_ar;
	}

	public void setTue_ar(ArrayList<String> tue_ar) {
		this.tue_ar = tue_ar;
	}

	public ArrayList<String> getWed_ar() {
		return wed_ar;
	}

	public void setWed_ar(ArrayList<String> wed_ar) {
		this.wed_ar = wed_ar;
	}

	public ArrayList<String> getThu_ar() {
		return thu_ar;
	}

	public void setThu_ar(ArrayList<String> thu_ar) {
		this.thu_ar = thu_ar;
	}

	public ArrayList<String> getFri_ar() {
		return fri_ar;
	}

	public void setFri_ar(ArrayList<String> fri_ar) {
		this.fri_ar = fri_ar;
	}

	public ArrayList<String> getSat_ar() {
		return sat_ar;
	}

	public void setSat_ar(ArrayList<String> sat_ar) {
		this.sat_ar = sat_ar;
	}

	public ArrayList<String> getSun_ar() {
		return sun_ar;
	}

	public void setSun_ar(ArrayList<String> sun_ar) {
		this.sun_ar = sun_ar;
	}

	public Bitmap getImages_ar() {
		return images_ar;
	}

	public void setImages_ar(Bitmap images_ar) {
		this.images_ar = images_ar;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getColor_header_str() {
		return color_header_str;
	}

	public void setColor_header_str(String color_header_str) {
		this.color_header_str = color_header_str;
	}

	public String getColor_footer_str() {
		return color_footer_str;
	}

	public void setColor_footer_str(String color_footer_str) {
		this.color_footer_str = color_footer_str;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
