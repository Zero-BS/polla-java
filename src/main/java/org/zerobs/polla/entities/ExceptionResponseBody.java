package org.zerobs.polla.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponseBody {
    private String messageTitle;
    private String messageText;
    private int internalCode;
}
