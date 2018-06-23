package eu.dandelion.Domain.Models;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DumpItem {
    public int Id;
    public int MapId;
    public ArrayList<PostingListItem> PostingList;
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(Id + "\t");
        sb.append(MapId + "\t");
        if(PostingList != null && PostingList.size() > 0)
        {
            sb.append(new String(Base64.getEncoder().encode(EncodePostingList(PostingList))) + "\n");
        } else {
            sb.append("null \n");
        }
        return sb.toString();
    }
    
    public byte[] EncodePostingList(List<PostingListItem> tuples)
    {
        byte[] postingList = new byte[tuples.size() * 4];
        int index = 0;
        for (PostingListItem item : tuples) {            
            postingList[(index * 4) + 0] = (byte) (item.Key >> 16);
            postingList[(index * 4) + 1] = (byte) (item.Key >> 8);
            postingList[(index * 4) + 2] = (byte) (item.Key);
            postingList[(index * 4) + 3] = (byte) (item.Value);
            index++;
        }
        
        return postingList;
    }
}

