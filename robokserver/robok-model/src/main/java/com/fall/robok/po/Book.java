package com.fall.robok.po;

import com.fall.robok.vo.BookOfSeller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FAll
 * @date 2022/9/24 21:35
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String bookId;

    private String openId;

    private String bookName;

    private Double price;

    private Integer pressId;

    private Integer status;

    private Integer audit;

    private Integer tagId;

    private String url1;

    private String url2;

    private String url3;

    private String description;

    private String timestamp;

    public Book(Builder builder) {
        this.bookId = builder.bookId;
        this.openId = builder.openId;
        this.bookName = builder.bookName;
        this.price = builder.price;
        this.pressId = builder.pressId;
        this.status = builder.status;
        this.audit = builder.audit;
        this.tagId = builder.tagId;
        this.url1 = builder.url1;
        this.url2 = builder.url2;
        this.url3 = builder.url3;
        this.description = builder.description;
        this.timestamp = builder.timestamp;
    }

    public static final class Builder {

        private String bookId;

        private String openId;

        private String bookName;

        private Double price;

        private Integer pressId;

        private Integer status;

        private Integer audit;

        private Integer tagId;

        private String url1;

        private String url2;

        private String url3;

        private String description;

        private String timestamp;

        public Book build(){
            return new Book(this);
        }

        public Builder bookId(String bookId) {
            this.bookId = bookId;
            return this;
        }

        public Builder openId(String openId) {
            this.openId = openId;
            return this;
        }

        public Builder bookName(String bookName) {
            this.bookName = bookName;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }

        public Builder pressId(Integer pressId) {
            this.pressId = pressId;
            return this;
        }

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder audit(Integer audit) {
            this.audit = audit;
            return this;
        }

        public Builder tagId(Integer tagId) {
            this.tagId = tagId;
            return this;
        }

        public Builder url1(String url1) {
            this.url1 = url1;
            return this;
        }

        public Builder url2(String url2) {
            this.url2 = url2;
            return this;
        }

        public Builder url3(String url3) {
            this.url3 = url3;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder bookOfSeller(BookOfSeller bookOfSeller) {
            this.openId = bookOfSeller.getOpenId();
            this.bookName = bookOfSeller.getBookName();
            this.price = bookOfSeller.getPrice();
            this.pressId = bookOfSeller.getPressId();
            this.status = bookOfSeller.getStatus();
            this.tagId = bookOfSeller.getTagId();
            this.description = bookOfSeller.getDescription();
            this.timestamp = bookOfSeller.getTimestamp();
            return this;
        }

    }

}
