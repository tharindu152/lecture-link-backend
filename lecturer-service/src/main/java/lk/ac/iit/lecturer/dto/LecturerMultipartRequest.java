package lk.ac.iit.lecturer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class LecturerMultipartRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String division;
    private String mapsLocation;
    @NotBlank
    @Email
    private String email;
    private String contactNo;
    private BigDecimal hourlyPayRate;
    private String preference;
    private String language;
    private Integer lecturingExperience;
    private String fieldOfWork;
    private String timePreference;
    private Long instituteId;
    private MultipartFile picture;
    @Min(0)
    @Max(5)
    private Integer currentRating;
    @Min(0)
    private Integer ratingsReceived;
    private Boolean isAssigned;
    private Boolean subscribed;
    private String status;

    public LecturerRequest toRequest() {
        return new LecturerRequest(name, division, mapsLocation, email, contactNo, hourlyPayRate,
                preference, language, lecturingExperience, fieldOfWork, timePreference, instituteId, null,
                currentRating, ratingsReceived, isAssigned, subscribed, status);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public BigDecimal getHourlyPayRate() {
        return hourlyPayRate;
    }

    public void setHourlyPayRate(BigDecimal hourlyPayRate) {
        this.hourlyPayRate = hourlyPayRate;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getLecturingExperience() {
        return lecturingExperience;
    }

    public void setLecturingExperience(Integer lecturingExperience) {
        this.lecturingExperience = lecturingExperience;
    }

    public String getFieldOfWork() {
        return fieldOfWork;
    }

    public void setFieldOfWork(String fieldOfWork) {
        this.fieldOfWork = fieldOfWork;
    }

    public String getTimePreference() {
        return timePreference;
    }

    public void setTimePreference(String timePreference) {
        this.timePreference = timePreference;
    }

    public Long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
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

    public Boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(Boolean assigned) {
        isAssigned = assigned;
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
