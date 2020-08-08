package app.uvsy.service.exceptions;

import org.apache.http.HttpConnectionFactory;
import org.github.serverless.api.exceptions.apigw.APIException;

import java.net.HttpURLConnection;

public class RecordConflictException extends APIException {

    public RecordConflictException() {
        super(
                "Conflict with the specified record",
                HttpURLConnection.HTTP_CONFLICT
        );
    }
}
