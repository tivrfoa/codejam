#include <cstdio>
#include <numeric>
#include <iostream>
#include <vector>
#include <set>
#include <cstring>
#include <string>
#include <map>
#include <cmath>
#include <ctime>
#include <algorithm>
#include <bitset>
#include <queue>
#include <sstream>
#include <deque>
#include <cassert>

using namespace std;

#define mp make_pair
#define pb push_back
#define rep(i,n) for(int i = 0; i < (n); i++)
#define re return
#define fi first
#define se second
#define sz(x) ((int) (x).size())
#define all(x) (x).begin(), (x).end()
#define sqr(x) ((x) * (x))
#define sqrt(x) sqrt(abs(x))
#define y0 y3487465
#define y1 y8687969
#define fill(x,y) memset(x,y,sizeof(x))
#define prev PREV
                         
typedef vector<int> vi;
typedef long long ll;
typedef long double ld;
typedef double D;
typedef pair<int, int> ii;
typedef vector<ii> vii;
typedef vector<string> vs;
typedef vector<vi> vvi;

template<class T> T abs(T x) { re x > 0 ? x : -x; }

int n;
int m;
string s;

int main () {
	int tt;
	cin >> tt;
	for (int it = 1; it <= tt; it++) {
		cin >> n;
		string sl = "";
		string sr = "";
		string mid = "";
		int ok = 1;
		for (int i = 0; i < n; i++) {
			string s;
			cin >> s;
			int x = 0, y = 0;
			x = y = 0;
			while (s[x] != '*') {
				if (x < sz (sl)) ok &= int (sl[x] == s[x]); else sl += s[x];
				x++;
			}
			while (s[sz (s) - y - 1] != '*') {
				if (y < sz (sr)) ok &= int (sr[y] == s[sz (s) - y - 1]); else sr += s[sz (s) - y - 1];
				y++;
			}
			for (int i = x + 1; i <= sz (s) - y - 2; i++)
				if (s[i] != '*')
					mid += s[i];
//			if (x + 1 <= sz (s) - y - 2) mid += s.substr (x + 1, sz (s) - y - x - 2);
		}
		cout.precision (20);
		cout << "Case #" << it << ": ";
		if (!ok) cout << "*"; else {
			reverse (all (sr));
			cout << sl + mid + sr;
		}
		cout << endl;
		fprintf (stderr, "%d / %d = %.2f | %.2f\n", it, tt, (double)clock () / CLOCKS_PER_SEC, ((double)clock () / it * tt) / CLOCKS_PER_SEC);
	}
	return 0;
}