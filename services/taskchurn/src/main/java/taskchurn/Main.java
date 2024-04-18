package taskchurn;

import bostonmodel.util.JsonTransformer;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serviceutil.Env;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(Env.getPort());
        get("/taskchurn", (request, response) -> {
            response.type("application/json");

//            int projectId;
//            LocalDate start;
//            LocalDate end;
//
//            try {
//                projectId = Integer.parseInt(request.queryParams("project_id"));
//            } catch (NumberFormatException ex) {
//                response.status(HttpStatus.SC_BAD_REQUEST);
//                logger.error("project_id must be an integer");
//                return "";
//            }
//
//            try {
//                start = LocalDate.parse(request.queryParams("start_date"));
//            } catch (DateTimeParseException ex) {
//                response.status(HttpStatus.SC_BAD_REQUEST);
//                logger.error("start_date must be a date in the format 'YYYY-MM-DD'");
//                return "";
//            }
//
//            try {
//                end = LocalDate.parse(request.queryParams("end_date"));
//            } catch (DateTimeParseException ex) {
//                response.status(HttpStatus.SC_BAD_REQUEST);
//                logger.error("end_date must be a date in the format 'YYYY-MM-DD'");
//                return "";
//            }
//
//            return TaskInertiaCalculator.calculate(response, projectId, start, end);

            return null;
        }, new JsonTransformer());
    }
}
