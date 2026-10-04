package lk.ac.iit.institute.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.multipart.MultipartFile;

public class InstituteMultipartRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String division;
    private String mapsLocation;
    private String telephone;
    private String description;
    private MultipartFile logo;
    @Min(0)
    @Max(5)
    private Integer currentRating;
    @Min(0)
    private Integer ratingsReceived;
    private Boolean subscribed;
    private String status;

    public InstituteRequest toRequest() {
        return new InstituteRequest(name, email, division, mapsLocation, telephone, description,
                null, currentRating, ratingsReceived, subscribed, status);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getMapsLocation() {
        return mapsLocation;
    }

    public void setMapsLocation(String mapsLocation) {
        this.mapsLocation = mapsLocation;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getLogo() {
        return logo;
    }

    public void setLogo(MultipartFile logo) {
        this.logo = logo;
    }

    public Integer getCurrentRating() {
        return currentRating;
    }

    public void setCurrentRating(Integer currentRating) {
        this.currentRating = currentRating;
    }

    public Integer getRatingsReceived() {
        return ratingsReceived;
    }

    public void setRatingsReceived(Integer ratingsReceived) {
        this.ratingsReceived = ratingsReceived;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
