package eu.dandelion.Domain.Models;

public class TestCase {
    public int FirstId;
    public int FirstMapId;
    public int SecondId;
    public int SecondMapId;
    public float Relatedness;
    
    public TestCase(String[] serializedTestCase)
    {
        if(serializedTestCase.length == 3)
        {
            FirstId = Integer.parseInt(serializedTestCase[0]);
            SecondId = Integer.parseInt(serializedTestCase[1]);
            Relatedness = Float.parseFloat(serializedTestCase[2]);
        }
    }
    
    public TestCase(int firstId, int secondId, float relatedness)
    {
        FirstId = firstId;
        SecondId = secondId;
        Relatedness = relatedness;
    }
}

