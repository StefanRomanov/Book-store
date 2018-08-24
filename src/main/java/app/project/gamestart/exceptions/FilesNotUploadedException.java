package app.project.gamestart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Publisher not found")
public class FilesNotUploadedException extends RuntimeException{
}