package com.hexagonaldemo.paymentapi.common.commandhandler;

import com.hexagonaldemo.paymentapi.common.model.Command;

public interface VoidCommandHandler<T extends Command> {

    void handle(T command);
}
