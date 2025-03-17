package lk.ac.iit.lecture_link.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LecturerTypeConverter implements Converter<String, Status> {

    @Override
    public Status convert(String source) {
        for (Status type : Status.values()) {
            if (type.getStatus().equalsIgnoreCase(source)){
                return type;
            }
        }
        return null;
    }
}
