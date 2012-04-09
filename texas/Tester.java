// I used this class to check my algorithms, it currently does nothing
package texas;
public class Tester
{
    private int a=0, handvalue=-1;
    private boolean test;
    int[] psuit = new int[4];
    int[] pface = new int[13];
    int[] phand = new int[52];
    int[] compsuit = new int[4];
    int[] compface = new int[13];
    int[] comphand = new int[52];
    int[] fvalues = new int[9];
    int[] svalues = new int[9];
    public Tester()
    {        
    }
    public void store(Object obj)
    {
        Card temp = (Card) obj;
        fvalues[a] = temp.getFaceValue()-1;
        svalues[a] = temp.getSuitValue();
        if (a < 2)
        {
            for (int b = 0 ; b < 4 ; b++)
                if (temp.getSuitValue() == b)
                    psuit[b]++;
            for (int c = 0 ; c < 13 ; c++)
                if (temp.getFaceValue() == c+1)
                    pface[c]++;
            for (int d = 0 ; d < 52 ; d++)
                if (((temp.getFaceValue()-1) + (temp.getSuitValue()*13)) == d)
                    phand[d]++;
         }
         else if (a > 1 && a < 4)
         {
            for (int e = 0 ; e < 4 ; e++)
                if (temp.getSuitValue() == e)
                    compsuit[e]++;
            for (int f = 0 ; f < 13 ; f++)
                if (temp.getFaceValue() == f+1)
                    compface[f]++;
            for (int g = 0 ; g < 52 ; g++)
                if (((temp.getFaceValue()-1) + (temp.getSuitValue()*13)) == g)
                    comphand[g]++;
         }
         else if (a < 9)
         {
            for (int h = 0 ; h < 4 ; h++)
                if (temp.getSuitValue() == h)
                {
                    psuit[h]++;
                    compsuit[h]++;
                }
            for (int i= 0 ; i < 13 ; i++)
                if (temp.getFaceValue() == i+1)
                {
                    pface[i]++;
                    compface[i]++;
                }
            for (int j = 0 ; j < 52 ; j++)
                if (((temp.getFaceValue()-1) + (temp.getSuitValue()*13)) == j)
                {
                    phand[j]++;
                    comphand[j]++;
                }
         }
         a++;
         /**
         if (a == 9)
         {
            for(int abc : fvalues)
                System.out.print(abc + " ");
            System.out.println("");
            for(int def : svalues)
                System.out.print(def + " ");
            System.out.println("");
         }
         */
        
    }
    public int CheckHand(String a)
    {
        String temp = a.toLowerCase();
        int phandvalue = 0, chandvalue = 0;
        if (temp.equals("player"))
        {
            if (rflush("player"))
                return handvalue;
            else if (sflush("player"))
                return handvalue;
            else if (fourokind("player"))
                return handvalue;
            else if (fullhouse("player"))
                return handvalue;
            else if (flush("player"))
                return handvalue;
            else if (straight("player"))
                return handvalue;
            else if (threeokind("player"))
                return handvalue;
            else if (twopair("player"))
                return handvalue;
            else if (pair("player"))
                return handvalue;
            else
            {
                highcard("player");
                return handvalue;
            }
            
        }
        else
        {
            if (rflush("comp"))
                return handvalue;
            else if (sflush("comp"))
                return handvalue;
            else if (fourokind("comp"))
                return handvalue;
            else if (fullhouse("comp"))
                return handvalue;
            else if (flush("comp"))
                return handvalue;
            else if (straight("comp"))
                return handvalue;
            else if (threeokind("comp"))
                return handvalue;
            else if (twopair("comp"))
                return handvalue;
            else if (pair("comp"))
                return handvalue;
            else
            {
                highcard("comp");
                return handvalue;
            }
        }
    }
    public void newhand()
    {
        for (int a = 0 ; a < 4 ; a++)
            psuit[a] = 0;
        for (int a = 0 ; a < 4 ; a++)
            compsuit[a] = 0;
        for (int a = 0 ; a < 13 ; a++)
            pface[a] = 0;
        for (int a = 0 ; a < 13 ; a++)
            compface[a] = 0;
        for (int a = 0 ; a < 52 ; a++)
            phand[a] = 0;
        for (int a = 0 ; a < 52 ; a++)
            comphand[a] = 0;
        a = 0;
        // System.out.println("handchecker - newhand");
    }
    public int[] getArray(String arr) // to test the store method
    {
        if (arr.equals("pface"))
            return pface;
        else if (arr.equals("psuit"))
            return psuit;
        else if (arr.equals("compface"))
            return compface;
        else if (arr.equals("compsuit"))
            return compsuit;
        else if (arr.equals("comphand"))
            return comphand;
        else
            return phand;
    }
    public boolean rflush(String h)
    {
        String temp = h.toLowerCase();
        int[] tester = {0,13,26,39};
        if (temp.equals("player"))
        {
            for (int y : tester)
            {
                    if ((phand[y] >=1) && (phand[y+12] >=1) && (phand[y+11] >=1)
                        && (phand[y+10] >=1) && (phand[y+9] >=1))
                    {
                        handvalue = 309;
                        return true;
                    }
            }
            return false;
        }
        else
        {
            for (int y : tester)
            {
                if ((comphand[y] >=1) && (comphand[y+12] >=1) && (comphand[y+11] >=1)
                    && (comphand[y+10] >=1) && (comphand[y+9] >=1))
                {
                    handvalue = 309;
                    return true;
                }
            }
            return false;
        }
    }
    public boolean sflush(String i)
    {
        String temp = i.toLowerCase();
        if (temp.equals("player"))
        {
            if (flush("player") && straight("player"))
            {
                for (int u = 0; u<9; u++)
                {
                    if (phand[u] >= 1 && phand[u+1] >= 1 && phand[u+2] >= 1
                    && phand[u+3] >= 1 && phand[u+4] >= 1)
                    {
                        handvalue = 300 + u;
                        return true;
                    }
                    else if (phand[0] >= 1 && phand[9] >= 1 && phand[10] >= 1
                    && phand[11] >= 1 && phand[12] >= 1)
                    {
                        return true;
                    }
                    else test = false;
                }
                if (!test)
                {
                    for (int v = 13; v<22; v++)
                    {
                        if (phand[v] >= 1 && phand[v+1] >= 1 && phand[v+2] >= 1
                        && phand[v+3] >= 1 && phand[v+4] >= 1)
                        {
                            handvalue = 300 + (v - 13);
                            return true;
                        }
                        else if (phand[13] >= 1 && phand[22] >= 1 && phand[23] >= 1
                        && phand[24] >= 1 && phand[25] >= 1)
                        {
                            return true;
                        }
                        else test = false;
                    }
                }
                if (!test)
                {
                    for (int w = 26; w<35; w++)
                    {
                        if (phand[w] >= 1 && phand[w+1] >= 1 && phand[w+2] >= 1
                        && phand[w+3] >= 1 && phand[w+4] >= 1)
                        {
                            handvalue = 300 + (w - 26);
                            return true;
                        }
                        else if (phand[26] >= 1 && phand[35] >= 1 && phand[36] >= 1
                        && phand[37] >= 1 && phand[38] >= 1)
                        {
                            return true;
                        }
                        else
                            test = false;
                    }
                }
                if (!test)
                {
                    for (int x = 39; x<48; x++)
                    {
                        if (phand[x] >= 1 && phand[x+1] >= 1 && phand[x+2] >= 1
                        && phand[x+3] >= 1 && phand[x+4] >= 1)
                        {
                            handvalue = 300 + (x - 39);
                            return true;
                        }
                        else if (phand[39] >= 1 && phand[48] >= 1 && phand[49] >= 1
                        && phand[50] >= 1 && phand[51] >= 1)
                        {
                            return true;
                        }
                        else
                            test = false;
                    }
                }
        } 
        else
        {
            if (flush("comp") && straight("comp"))
            {
                for (int u = 0; u<9; u++)
                {
                    if (comphand[u] >= 1 && comphand[u+1] >= 1 && comphand[u+2] >= 1
                    && comphand[u+3] >= 1 && comphand[u+4] >= 1)
                    {
                        handvalue = 300 + u;
                            return true;
                    }
                    else if (comphand[0] >= 1 && comphand[9] >= 1 && comphand[10] >= 1
                    && comphand[11] >= 1 && comphand[12] >= 1)
                    {
                        return true;
                    }
                    else
                        test = false;
                }
                if (!test)
                {
                    for (int v = 13; v<22; v++)
                    {
                        if (comphand[v] >= 1 && comphand[v+1] >= 1 && comphand[v+2] >= 1
                        && comphand[v+3] >= 1 && comphand[v+4] >= 1)
                        {
                            handvalue = 300 + (v - 13);
                            return true;
                        }
                        else if (comphand[13] >= 1 && comphand[22] >= 1 && comphand[23] >= 1
                        && comphand[24] >= 1 && comphand[25] >= 1)
                        {
                            return true;
                        }
                        else
                            test = false;
                    }
                }
                if (!test)
                {
                    for (int w = 26; w<35; w++)
                    {
                        if (comphand[w] >= 1 && comphand[w+1] >= 1 && comphand[w+2] >= 1
                        && comphand[w+3] >= 1 && comphand[w+4] >= 1)
                        {
                            handvalue = 300 + (w - 26);
                            return true;
                        }
                        else if (comphand[26] >= 1 && comphand[35] >= 1 && comphand[36] >= 1
                        && comphand[37] >= 1 && comphand[38] >= 1)
                        {
                            return true;
                        }
                        else
                            test = false;
                    }
                }
                if (!test)
                {
                    for (int x = 39; x<48; x++)
                    {
                        if (comphand[x] >= 1 && comphand[x+1] >= 1 && comphand[x+2] >= 1
                        && comphand[x+3] >= 1 && comphand[x+4] >= 1)
                        {
                            handvalue = 300 + (x - 39);
                            return true;
                        }
                        else if (comphand[39] >= 1 && comphand[48] >= 1 && comphand[49] >= 1
                        && comphand[50] >= 1 && comphand[51] >= 1)
                        {
                            return true;
                        }
                        else
                            test = false;
                    }
                }
            }
        }
    }
        return test;
    }
    public boolean fourokind(String j)
    {
        String temp = j.toLowerCase();
        if (temp.equals("player"))
        {
            for (int z=0 ; z<13 ; z++)
            {
                if (pface[z] >=4)
                {
                    if (z == 0)
                        handvalue = 299;
                    else handvalue = 287 + (z - 1);
                    return true;
                }
            }
            return false;
        } 
        else
        {
            for (int z=0 ; z<13 ; z++)
            {
                if (compface[z] >=4)
                {
                    if (z == 0)
                        handvalue = 299;
                    else handvalue = 287 + (z - 1);
                    return true;
                }
            } 
            return false;
        }
    }
    public boolean fullhouse(String k)
    {
        String temp = k.toLowerCase();
        int first = -1;
        if (temp.equals("player"))
        {
            for (int ae=0;ae<13;ae++)
            {
                if (pface[ae] >=3)
                {
                    first = ae;
                    ae = 14;
                }
                else
                    test = false;
            }
            for (int af=0;af<13;af++)
            {
                if (pface[af] >=2 && af != first && first >=0)
                {
                    if (first == 0)
                        handvalue = 130 + (144 + (af-3));
                    else if (af == 0)
                        handvalue = 130 + (((first-1)*12) + 11);
                    else handvalue = 130 + (((first-1)*12) + (af-2));
                    return true;
                }
                else
                    test = false;
            }
        } 
        else
        {
            for (int ae=0;ae<13;ae++)
            {
                if (compface[ae] >=3)
                {
                    first = ae;
                    ae = 14;
                }
                else
                    test = false;
            }
            for (int af=0;af<13;af++)
            {
                if (compface[af] >=2 && af != first && first >= 0)
                {
                    if (first == 0)
                        handvalue = 130 + (144 + (af-3));
                    else if (af == 0)
                        handvalue = 130 + (((first-1)*12) + 11);
                    else handvalue = 130 + (((first-1)*12) + (af-2));
                    return true;
                }
                else
                    test = false;
            }
        }
        return test;
    }
    public boolean flush(String l)
    {
        String temp = l.toLowerCase();
        if (temp.equals("player"))
        {
            for (int q = 0; q<4; q++)
            {
                if (psuit[q] >= 5)
                {
                    for (int high = ((q+1)*13)-1 ; high >= q*13 ; high ++)
                    {
                        if (phand[high] >= 1)
                        {
                            if (high == 0)
                                handvalue = 129;
                            else handvalue = 122 + (high - 6);
                        }
                        return true;
                    }
                }
            }
            return false;
        }
        else
        {
            for (int q = 0; q<4; q++)
            {
                if (compsuit[q] >= 5)
                {
                    for (int high = ((q+1)*13)-1 ; high >= q*13 ; high ++)
                    {
                        if (comphand[high] >= 1)
                        {
                            if (high == 0)
                                handvalue = 129;
                            else handvalue = 122 + (high - 6);
                        }
                        return true;
                    }
                }
            }
            return false; 
        }
    }
    public boolean straight(String m)
    {
        String temp = m.toLowerCase();
        if (temp.equals("player"))
        {
            for (int s = 0; s<9; s++)
            {
                if ((pface[s] >= 1) && (pface[s+1] >= 1) && (pface[s+2] >= 1)
                && (pface[s+3] >= 1) && (pface[s+4] >= 1))
                {
                    handvalue = 112 + s;
                    return true;
                }
            }
            if ((pface[0] >= 1) && (pface[9] >= 1) && (pface[10] >= 1) && (pface[11] >= 1) && (pface[12] >= 1))
                return true;
            else
                return false;
        } 
        else
        {
            for (int t = 0; t<9; t++)
            {
                if ((compface[t] >= 1) && (compface[t+1] >= 1) && (compface[t+2] >= 1)
                && (compface[t+3] >= 1) && (compface[t+4] >= 1))
                {
                    handvalue = 112 + t;
                    return true;
                }
            }
            if ((compface[0] >= 1) && (compface[9] >= 1) && (compface[10] >= 1) && (compface[11] >= 1) && (compface[12] >= 1))
                return true;
            else
                return false;
        }
    }
    public boolean threeokind(String n)
    {
        String temp = n.toLowerCase();
        if (temp.equals("player"))
        {
            for (int aa=0;aa<13;aa++)
            {
                if (pface[aa] >=3)
                {
                    if (aa == 0)
                        handvalue = 111;
                    else handvalue = 99 + (aa-1);
                    return true;
                }
            }
            return false;
        } 
        else
        {
            for (int aa=0;aa<13;aa++)
            {
                if (compface[aa] >=3)
                {
                    if (aa == 0)
                        handvalue = 111;
                    else handvalue = 99 + (aa-1);
                    return true;
                }
            }
            return false;
        }
    }
    public boolean twopair(String o)
    {
        String temp = o.toLowerCase();
        int first = -1, sum=0;
        int[] counter = {1,2,3,4,5,6,7,8,9,10,11};
        if (temp.equals("player"))
        {
            for (int ac=0;ac<12;ac++)
            {
                if (pface[ac] >=2)
                {
                    first = ac;
                    ac = 14;
                }
            }
            if (first == -1)
                return false;
            for (int ad=first+1;ad<13;ad++)
            {
                if (pface[ad] >=2)
                {
                    if (first == 0)
                        handvalue = 87 + (ad-1);
                    else
                    {
                        for (int cnt = ad-3 ; cnt > -1 ; cnt--)
                            sum += counter[cnt];
                        handvalue = 20 + sum + first;
                    }
                    return true;
                }
            }
            return false;
        } 
        else
        {
            for (int ac=0;ac<12;ac++)
            {
                if (compface[ac] >=2)
                {
                    first = ac;
                    ac = 14;
                }
            }
            if (first == -1)
                return false;
            for (int ad=first+1;ad<13;ad++)
            {
                if (compface[ad] >=2)
                {
                    if (first == 0)
                        handvalue = 87 + (ad-1);
                    else
                    {
                        for (int cnt = ad-3 ; cnt > -1 ; cnt--)
                            sum += counter[cnt];
                        handvalue = 20 + sum + first;
                    }
                    return true;
                }
            }
            return false;
        }
    }
    public boolean pair(String p)
    {
        String temp = p.toLowerCase();
        if (temp.equals("player"))
        {
            for (int ab=0;ab<13;ab++)
            {
                if (pface[ab] >=2)
                {
                    if (ab == 0)
                        handvalue = 20;
                    else handvalue = 8 + (ab-1);
                    return true;
                }
            } 
            return false;
        } 
        else
        {
            for (int ab=0 ; ab<13 ; ab++)
            {
                if (compface[ab] >=2)
                {
                    if (ab == 0)
                        handvalue = 20;
                    else handvalue = 8 + (ab-1);
                    return true;
                }
            }
            return false;
        }
    }
    public boolean highcard(String highc)
    {
        String temp = highc.toLowerCase();
        if (temp.equals("player"))
        {
            if (pface[0] >= 1)
            {
                handvalue = 7;
                return true;
            }
            for (int fce = 12 ; fce > -1 ; fce++)
                if (pface[fce] >= 1)
                    handvalue = fce-5;
            return true;
        }
        else
        {
            if (pface[0] >= 1)
            {
                handvalue = 7;
                return true;
            }
            for (int fce = 12 ; fce > -1 ; fce++)
                if (compface[fce] >= 1)
                    handvalue = fce-5;
            return true;
        }
    }
}