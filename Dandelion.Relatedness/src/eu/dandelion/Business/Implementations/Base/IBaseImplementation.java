package eu.dandelion.Business.Implementations.Base;

public interface IBaseImplementation {
    float Relatedness(int a, int b) throws Exception ;
    void ReadDump(String dump) throws Exception ;
    boolean isDumpLoaded();
}
