package route;
@FunctionalInterface
public interface RouteHandler<E> {

    void handler(E var);

}
