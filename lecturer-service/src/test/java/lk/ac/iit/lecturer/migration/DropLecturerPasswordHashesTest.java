package lk.ac.iit.lecturer.migration;

import db.migration.V1__drop_lecturer_password_hashes;
import org.flywaydb.core.api.migration.Context;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DropLecturerPasswordHashesTest {
    @Test
    void dropsTheLegacyPasswordColumnWhenPresent() throws Exception {
        try (var connection = DriverManager.getConnection("jdbc:h2:mem:lecturer-migration")) {
            try (var statement = connection.createStatement()) {
                statement.execute("CREATE TABLE lecturers (id BIGINT, password VARCHAR(255) NOT NULL)");
            }

            Context context = mock(Context.class);
            when(context.getConnection()).thenReturn(connection);
            new V1__drop_lecturer_password_hashes().migrate(context);

            try (var columns = connection.getMetaData().getColumns(
                    connection.getCatalog(), null, "lecturers", "password")) {
                assertFalse(columns.next());
            }
        }
    }
}
