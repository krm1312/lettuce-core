package com.lambdaworks.redis.api.async;

import java.util.List;
import java.util.Map;
import com.lambdaworks.redis.protocol.CommandArgs;
import com.lambdaworks.redis.protocol.ProtocolKeyword;
import com.lambdaworks.redis.output.CommandOutput;
import com.lambdaworks.redis.RedisFuture;

/**
 * 
 * Asynchronous executed commands for basic commands.
 * 
 * @param <K> Key type.
 * @param <V> Value type.
 * @author Mark Paluch
 * @since 4.0
 * @generated by com.lambdaworks.apigenerator.CreateAsyncApi
 */
public interface BaseRedisAsyncCommands<K, V> extends AutoCloseable {

    /**
     * Post a message to a channel.
     * 
     * @param channel the channel type: key
     * @param message the message type: value
     * @return Long integer-reply the number of clients that received the message.
     */
    RedisFuture<Long> publish(K channel, V message);

    /**
     * Lists the currently *active channels*.
     * 
     * @return List&lt;K&gt; array-reply a list of active channels, optionally matching the specified pattern.
     */
    RedisFuture<List<K>> pubsubChannels();

    /**
     * Lists the currently *active channels*.
     * 
     * @param channel the key
     * @return List&lt;K&gt; array-reply a list of active channels, optionally matching the specified pattern.
     */
    RedisFuture<List<K>> pubsubChannels(K channel);

    /**
     * Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels.
     *
     * @param channels channel keys
     * @return array-reply a list of channels and number of subscribers for every channel.
     */
    RedisFuture<Map<K, Long>> pubsubNumsub(K... channels);

    /**
     * Returns the number of subscriptions to patterns.
     * 
     * @return Long integer-reply the number of patterns all the clients are subscribed to.
     */
    RedisFuture<Long> pubsubNumpat();

    /**
     * Echo the given string.
     * 
     * @param msg the message type: value
     * @return V bulk-string-reply
     */
    RedisFuture<V> echo(V msg);

    /**
     * Return the role of the instance in the context of replication.
     *
     * @return List&lt;Object&gt; array-reply where the first element is one of master, slave, sentinel and the additional
     *         elements are role-specific.
     */
    RedisFuture<List<Object>> role();

    /**
     * Ping the server.
     * 
     * @return String simple-string-reply
     */
    RedisFuture<String> ping();

    /**
     * Switch connection to Read-Only mode when connecting to a cluster.
     *
     * @return String simple-string-reply.
     */
    RedisFuture<String> readOnly();

    /**
     * Switch connection to Read-Write mode (default) when connecting to a cluster.
     *
     * @return String simple-string-reply.
     */
    RedisFuture<String> readWrite();

    /**
     * Close the connection.
     * 
     * @return String simple-string-reply always OK.
     */
    RedisFuture<String> quit();

    /**
     * Wait for replication.
     * 
     * @param replicas minimum number of replicas
     * @param timeout timeout in milliseconds
     * @return number of replicas
     */
    RedisFuture<Long> waitForReplication(int replicas, long timeout);

    /**
     * Dispatch a command to the Redis Server. Please note the command output type must fit to the command response.
     *
     * @param type the command, must not be {@literal null}.
     * @param output the command output, must not be {@literal null}.
     * @param <T> response type
     * @return the command response
     */
    <T> RedisFuture<T> dispatch(ProtocolKeyword type, CommandOutput<K, V, T> output);

    /**
     * Dispatch a command to the Redis Server. Please note the command output type must fit to the command response.
     *
     * @param type the command, must not be {@literal null}.
     * @param output the command output, must not be {@literal null}.
     * @param args the command arguments, must not be {@literal null}.
     * @param <T> response type
     * @return the command response
     */
    <T> RedisFuture<T> dispatch(ProtocolKeyword type, CommandOutput<K, V, T> output, CommandArgs<K, V> args);

    /**
     * Close the connection. The connection will become not usable anymore as soon as this method was called.
     */
    void close();

    /**
     * 
     * @return true if the connection is open (connected and not closed).
     */
    boolean isOpen();

    /**
     * Reset the command state. Queued commands will be canceled and the internal state will be reset. This is useful when the
     * internal state machine gets out of sync with the connection.
     */
    void reset();

    /**
     * Disable or enable auto-flush behavior. Default is {@literal true}. If autoFlushCommands is disabled, multiple commands
     * can be issued without writing them actually to the transport. Commands are buffered until a {@link #flushCommands()} is
     * issued. After calling {@link #flushCommands()} commands are sent to the transport and executed by Redis.
     *
     * @param autoFlush state of autoFlush.
     */
    void setAutoFlushCommands(boolean autoFlush);

    /**
     * Flush pending commands. This commands forces a flush on the channel and can be used to buffer ("pipeline") commands to
     * achieve batching. No-op if channel is not connected.
     */
    void flushCommands();
}
