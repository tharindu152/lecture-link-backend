package lk.ac.iit.lecturer.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " not found: " + id);
    }
}
