package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.ResultSet;
import java.sql.Statement;

public class V1__drop_lecturer_password_hashes extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        if (!columnExists(context, "lecturers", "password")) {
            return;
        }

        try (Statement statement = context.getConnection().createStatement()) {
            statement.execute("ALTER TABLE lecturers DROP COLUMN password");
        }
    }

    private boolean columnExists(Context context, String tableName, String columnName) throws Exception {
        var connection = context.getConnection();
        try (ResultSet columns = connection.getMetaData()
                .getColumns(connection.getCatalog(), null, "%", "%")) {
            while (columns.next()) {
                if (tableName.equalsIgnoreCase(columns.getString("TABLE_NAME"))
                        && columnName.equalsIgnoreCase(columns.getString("COLUMN_NAME"))) {
                    return true;
                }
            }
            return false;
        }
    }
}
