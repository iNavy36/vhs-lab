package com.example.lab.model;

import java.time.LocalDate;

public class RentalMapped {
    private Long rental_id;
    private LocalDate rental_date;
    private LocalDate return_date;
    private String movie_title;
    private String user_name;

    RentalMapped(Long rental_id, LocalDate rental_date, LocalDate return_date, String movie_title,
            String user_name) {
        this.rental_id = rental_id;
        this.rental_date = rental_date;
        this.return_date = return_date;
        this.movie_title = movie_title;
        this.user_name = user_name;
    }

    public Long getRental_id() {
        return rental_id;
    }

    public void setRental_id(Long rental_id) {
        this.rental_id = rental_id;
    }

    public LocalDate getRental_date() {
        return rental_date;
    }

    public void setRental_date(LocalDate rental_date) {
        this.rental_date = rental_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rental_id == null) ? 0 : rental_id.hashCode());
        result = prime * result + ((rental_date == null) ? 0 : rental_date.hashCode());
        result = prime * result + ((return_date == null) ? 0 : return_date.hashCode());
        result = prime * result + ((movie_title == null) ? 0 : movie_title.hashCode());
        result = prime * result + ((user_name == null) ? 0 : user_name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RentalMapped other = (RentalMapped) obj;
        if (rental_id == null) {
            if (other.rental_id != null)
                return false;
        } else if (!rental_id.equals(other.rental_id))
            return false;
        if (rental_date == null) {
            if (other.rental_date != null)
                return false;
        } else if (!rental_date.equals(other.rental_date))
            return false;
        if (return_date == null) {
            if (other.return_date != null)
                return false;
        } else if (!return_date.equals(other.return_date))
            return false;
        if (movie_title == null) {
            if (other.movie_title != null)
                return false;
        } else if (!movie_title.equals(other.movie_title))
            return false;
        if (user_name == null) {
            if (other.user_name != null)
                return false;
        } else if (!user_name.equals(other.user_name))
            return false;
        return true;
    }

}
