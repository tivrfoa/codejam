#include <iostream>
#include <sstream>
#include <fstream>
#include <string>
#include <vector>
#include <deque>
#include <queue>
#include <stack>
#include <set>
#include <map>
#include <algorithm>
#include <functional>
#include <utility>
#include <bitset>
#include <cmath>
#include <cstdlib>
#include <ctime>
#include <cstdio>

using namespace std;

#define REP(i,n) for((i)=0;(i)<(int)(n);(i)++)
#define snuke(c,itr) for(__typeof((c).begin()) itr=(c).begin();itr!=(c).end();itr++)

int gcd(int x, int y){
	return x ? gcd(y%x, x) : y;
}

int N;
int a[310],b[310];
vector <pair <int, int> > v;
	
void main2(void){
	int N,i,j;
	
	cin >> N;
	REP(i,N) scanf("%d%d", &a[i], &b[i]);
	
	v.clear();
	REP(i,N) REP(j,N){
		int dx = a[i] - a[j];
		int dy = b[j] - b[i];
		if(dx > 0 && dy > 0){
			int g = gcd(dx, dy);
			v.push_back(make_pair(dx / g, dy / g));
		}
	}
	
	sort(v.begin(),v.end());
	int sz = v.size();
	
	int ans = 1;
	REP(i,sz) if(i == 0 || v[i] != v[i-1]) ans++;
	
	cout << ans << endl;
}	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

int main(void){
	int TC,tc;
	cin >> TC;
	REP(tc,TC){
		printf("Case #%d: ", tc + 1);
		main2();
	}
	return 0;
}
