#include <bits/stdc++.h>
using namespace std;

#define ll long long
#define ar array

int b, a[100], samepair, diffpair;

int qry(int i) {
	cout << i+1 << endl;
	int ans;
	cin >> ans;
	return ans;
}

void findpair(int i) {
	//i b-1-i
	a[i]=qry(i);
	a[b-1-i]=qry(b-1-i);
	if(samepair==-1&&a[i]==a[b-1-i])
		samepair=i;
	if(diffpair==-1&&a[i]!=a[b-1-i])
		diffpair=i;
}

void findchange() {
	bool comp=0;
	if(samepair!=-1&&a[samepair]!=qry(samepair))
		comp=1;
	if(samepair==-1)
		qry(0);
	bool rev=comp;
	if(diffpair!=-1&&a[diffpair]!=qry(diffpair))
		rev=!comp;
	if(diffpair==-1)
		qry(0);
	if(comp)
		for(int i=0; i<b; ++i)
			a[i]=!a[i];
	if(rev)
		for(int i=0; i<b/2; ++i)
			swap(a[i], a[b-1-i]);
}

void solve() {
	samepair=-1;
	diffpair=-1;
	//find 5 pairs
	int c=0;
	for(; c<5; ++c) {
		findpair(c);
	}
	findchange();
	while(c<b/2) {
		int i=0;
		for(; i<4&&c<b/2; ++i, ++c)
			findpair(c);
		if(i==4) {
			//all 4 happened, change happened
			findchange();
		}
	}

	for(int i=0; i<b; ++i)
		cout << a[i];
	cout << endl;
	char ok;
	cin >> ok;
	if(ok=='N')
		exit(0);
}

int main() {
	ios::sync_with_stdio(0);
	cin.tie(0);

	int t;
	cin >> t >> b;
	for(int i=1; i<=t; ++i) {
		solve();
	}
}
