/*
 * Copyright (c) 2010-2017. Axon Framework
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.extensions.amqp;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Defines the properties to use for forwarding Event Messages published ont he Event Bus to an AMQP Message Broker.
 */
@ConfigurationProperties(prefix = "axon.amqp")
public class AMQPProperties {

    /**
     * Name of the exchange to forward Event messages to.
     */
    private String exchange;

    /**
     * Whether the messages should be sent with the 'durable' flag. Defaults to true.
     */
    private boolean durableMessages = true;

    /**
     * Defines how transactions around publishing should be managed (none (default), transactional or publisher-ack).
     */
    private TransactionMode transactionMode = TransactionMode.NONE;

    /**
     * Returns the name of the exchange to forward Event messages to.
     *
     * @return the name of the exchange to forward Event messages to.
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * Sets the name of the exchange to forward Event Messages to.
     *
     * @param exchange the name of the exchange to forward Event Messages to.
     */
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * Indicates whether and how batches of events should be handled transactionally.
     *
     * @return the currently configured {@link TransactionMode}.
     */
    public TransactionMode getTransactionMode() {
        return transactionMode;
    }

    /**
     * Sets whether and how batches of events should be sent to the server transactionally.
     *
     * @param transactionMode The {@link TransactionMode} to use.
     */
    public void setTransactionMode(TransactionMode transactionMode) {
        this.transactionMode = transactionMode;
    }

    /**
     * Indicates whether messages should be durable.
     *
     * @return whether messages should be durable.
     */
    public boolean isDurableMessages() {
        return durableMessages;
    }

    /**
     * Sets whether messages should have the "durable" flag when sent to the message broker.
     *
     * @param durableMessages whether messages should have the "durable" flag when sent to the message broker.
     */
    public void setDurableMessages(boolean durableMessages) {
        this.durableMessages = durableMessages;
    }

    public enum TransactionMode {

        /**
         * Indicates batches of messages should be sent using a transaction. While this provides ACID guarantees, it
         * has a negative impact on performance.
         */
        TRANSACTIONAL,

        /**
         * Indicates batches of messages should be sent and confirmed using Publisher Acknowledgements. This ensures
         * the sender of messages waits for an Acknowledgement from the server before moving on. However, it doesn't
         * provide for full ACID guarantees. When delivery fails, some messages may still have been delivered.
         * <p>
         * This setting does ensure that messages generated by the same Aggregate are published to the exchange in the
         * order they were published by the aggregate.
         * <p>
         * Note that not all AMQP implementations support publisher acks.
         */
        PUBLISHER_ACK,

        /**
         * No transactional guarantees. Batches of messages are sent and no confirmation is requested from the server.
         */
        NONE

    }
}
