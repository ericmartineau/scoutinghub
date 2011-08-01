package scoutinghub

/**
 * Created by IntelliJ IDEA.
 * User: ericm
 * Date: 7/31/11
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
interface SeedScript {
    int getOrder();
    void execute();
    String getName();
}
