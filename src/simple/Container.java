package simple;

import utils.Relation;

import java.util.HashMap;
import java.util.Map;

public class Container implements Injector {

    private enum ObjectType {CONSTANT, FACTORY, SINGLETON}

    private final Map<String, Relation<ObjectType, Object, Object>> services;

    public Container() {
        this.services = new HashMap<>();
    }

    @Override
    public void registerConstant(String name, Object value) throws DependencyException {
        if(services.containsKey(name))
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        services.put(name, new Relation<>(ObjectType.CONSTANT, value, null));
    }

    @Override
    public void registerFactory(String name, Factory creator, String... parameters) throws DependencyException {
        if(services.containsKey(name) || !services.containsKey(parameters[0])) /// DUDAAAAAAA
            throw new DependencyException(new DependencyException("The key already exists in the map."));
        services.put(name, new Relation<>(ObjectType.FACTORY, creator, parameters));
    }

    @Override
    public void registerSingleton(String name, Factory creator, String... parameters) throws DependencyException {

    }

    @Override
    public Object getObject(String name) throws DependencyException {
        Relation<ObjectType, Object, Object> value = services.get(name);
        if(value == null)
            throw new DependencyException(new DependencyException("The key was not found in the map."));

        switch(value.getFirst()) {
            case FACTORY:
                String[] values = (String[]) value.getThird();

                Object[] params = new Object[values.length];
                int cont = 0;

                for(String x : values){
                    params[cont] = (int) this.getObject(x);
                    cont++;
                }
                return ((Factory)value.getSecond()).create(params);
            case CONSTANT:
                return value.getSecond();
        }

        throw new DependencyException(new DependencyException("The ObjectType was neither SERVICE nor CONSTANT."));
    }
}
