package com.hexagonaldemo.paymentapi.common.commandhandler;

import com.hexagonaldemo.paymentapi.common.model.Command;

public interface CommandHandler<E, T extends Command> {

    E handle(T command);
}
