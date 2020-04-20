#include <bits/stdc++.h>
using namespace std;

#define ll long long
#define ar array

string ans;
char ch[4]={'S', 'N', 'W', 'E'};

ll tx(ll a, ll x, int i) {
	if(i==2)
		x+=1ll<<a;
	if(i==3)
		x-=1ll<<a;
	return x;
}

ll ty(ll a, ll y, int i) {
	if(i==0)
		y+=1ll<<a;
	if(i==1)
		y-=1ll<<a;
	return y;
}

bool ok(ll a, ll x, ll y) {
	return (1ll<<(a+1))-1>=abs(x)+abs(y);
}

void rec(ll a, ll x, ll y) {
	if(a==-1)
		return;
	for(int i=0; i<4; ++i) {
		ll x2=tx(a, x, i), y2=ty(a, y, i);
		if(ok(a-1, x2, y2)) {
			rec(a-1, x2, y2);
			ans+=ch[i];
			break;
		}
	}
}

void solve() {
	ans="";
	ll x, y;
	cin >> x >> y;
	if((x+y)%2==0) {
		cout << "IMPOSSIBLE\n";
		return;
	}
	ll s=abs(x)+abs(y);
	ll a=0;
	while((1ll<<a)-1<s)
		++a;
	//0..a-1
	rec(a-1, x, y);
	cout << ans << "\n";
}

int main() {
	ios::sync_with_stdio(0);
	cin.tie(0);

	int t;
	cin >> t;
	for(int i=1; i<=t; ++i) {
		cout << "Case #" << i << ": ";
		solve();
	}
}