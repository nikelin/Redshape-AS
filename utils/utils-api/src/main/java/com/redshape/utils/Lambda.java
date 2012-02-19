package com.redshape.utils;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 2/17/12 {6:00 PM}
 */
public abstract class Lambda<T> implements ILambda<T> {
    private Object[] arguments;

    public Lambda() {
        this( new Object[] {} );
    }

    public Lambda( Object... arguments ) {
        this.arguments = arguments;
    }
    
    protected Object[] getArguments() {
        return this.arguments;
    }

    protected void assertTrue( Boolean condition, String message ) {
        if ( !condition ) {
            throw new IllegalArgumentException(message);
        }
    }

    protected void assertArgumentType( Object argument, Class<?> type ) {
        this.assertArgumentsType( new Object[] { argument }, type );
    }

    protected void assertArgumentsType( Object[] arguments, Class<?> type ) {
        for ( Object argument : arguments ) {
            if ( !argument.getClass().equals(type) ) {
                throw new IllegalArgumentException("Wrong argument type!");
            }
        }
    }

    protected void assertArgumentsCount( Object[] actual, int count ) {
        if ( actual.length != count ) {
            throw new IllegalArgumentException("Wrong arguments count " + actual.length + " when expected " + count );
        }
    }

    @Override
    public T invoke() throws InvocationException {
        return this.invoke( new Object[] {} );
    }

    @Override
    public ILambda<T> pass(Object... arguments) {
       Object[] newArguments = new Object[this.arguments.length + arguments.length];
       int index;
       for ( index = 0; index < this.arguments.length; index++ ) {
           newArguments[index] = this.arguments[index];
       }

        for ( index += 1; index < arguments.length; index++ ) {
            newArguments[index] = arguments[index - arguments.length];
        }

        this.arguments = newArguments;
        return this;
    }


}
