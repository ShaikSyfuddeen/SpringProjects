package com.springProject.onlineBookStore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="book")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="author")
	private String author;
	
	@Column(name="price")
	private int price;
	
	@Column(name="copies_left")
	private int copiesLeft;

	public Book() {
	}

	public Book(int id, String title, String description, String author, int price) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCopiesLeft() {
		return copiesLeft;
	}

	public void setCopiesLeft(int copiesLeft) {
		this.copiesLeft = copiesLeft;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", description=" + description + ", author=" + author
				+ ", price=" + price + ", copiesLeft=" + copiesLeft + "]";
	}
	
}
