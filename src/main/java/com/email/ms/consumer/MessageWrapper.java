package com.email.ms.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageWrapper {
    private Email email;
    private HTMLSettings htmlSettings;
}