#include <stdio.h>
#include <bits/stdc++.h>

using namespace std;

int main () {
    
    map<char, int> m;
    m['l'] = 2;

    pair<char, int> p ('l', m['l']);

    cout << p.first << " " << p.second << endl;

    cout << get<1>(p) << endl;
    
    m['n'] = 3;
    vector<pair<char, int>> vp(begin(m), end(m));

    for (auto p1 : vp) {
        cout << p1.first << " " << p1.second << endl;
    }

    return 0;
}
