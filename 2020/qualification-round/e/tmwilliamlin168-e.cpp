#include <bits/stdc++.h>
using namespace std;

#define ll long long
#define ar array

const int mxN=50;
int n, k, ans[mxN][mxN];

void solve() {
	memset(ans, 0, sizeof(ans));
	cin >> n >> k;
	//diagonal all same
	for(int a=1; a<=n; ++a) {
		if(n*a==k) {
			for(int i=0; i<n; ++i)
				for(int j=0; j<n; ++j)
					ans[i][j]=(j-i+n+a-1)%n+1;
		}
	}
	//a, b where n-2 elements are a and the last 2 are b
	if(n>3&&n%2==1) {
		for(int a=1; a<=n; ++a) {
			for(int b=1; b<=n; ++b) {
				if(b==a||(n-2)*a+2*b!=k)
					continue;
				ans[n-2][n-2]=ans[n-1][n-1]=b;
				ans[n-1][n-2]=ans[n-2][n-1]=a;
				vector<int> p;
				for(int i=1; i<=n; ++i)
					if(i^a&&i^b)
						p.push_back(i);
				for(int i=0; i<n-2; ++i) {
					for(int j=0; j<n-2; ++j) {
						int e=p[(i+j)%(n-2)];
						if(i==j) {
							ans[i][j]=a;
							ans[i][n-2]=ans[n-2][j]=e;
						} else if((j-i+n-2)%(n-2)==1) {
							ans[i][j]=b;
							ans[i][n-1]=ans[n-1][j]=e;
						} else
							ans[i][j]=e;
					}
				}
			}
		}
	}
	//a, b, c where first n-2 are a, last 2 are b and c
	//bottom right 4x4
	if(n==4||n>7&&n%2==1) {
		for(int a=1; a<=n; ++a) {
			for(int b=1; b<=n; ++b) {
				for(int c=1; c<=n; ++c) {
					if(b==a||c==a||(n-2)*a+b+c!=k)
						continue;
					vector<int> p;
					for(int i=1; i<=n; ++i)
						if(i^a&&i^b&&i^c)
							p.push_back(i);
					int d=p.back(), c2=c;
					p.pop_back();
					bool b1=b==c;
					if(b1) {
						c2=p.back();
						p.pop_back();
						ans[n-4][n-3]=ans[n-3][n-4]=ans[n-2][n-2]=ans[n-1][n-1]=b;
						ans[n-4][n-2]=ans[n-3][n-1]=ans[n-2][n-4]=ans[n-1][n-3]=c2;
						ans[n-4][n-1]=ans[n-3][n-2]=ans[n-2][n-3]=ans[n-1][n-4]=d;
					} else {
						ans[n-4][n-1]=ans[n-3][n-4]=ans[n-2][n-2]=ans[n-1][n-3]=b;
						ans[n-4][n-3]=ans[n-3][n-2]=ans[n-2][n-4]=ans[n-1][n-1]=c2;
						ans[n-4][n-2]=ans[n-3][n-1]=ans[n-2][n-3]=ans[n-1][n-4]=d;
					}
					ans[n-4][n-4]=ans[n-3][n-3]=ans[n-2][n-1]=ans[n-1][n-2]=a;
					for(int i=0; i<n-4; ++i) {
						for(int j=0; j<n-4; ++j) {
							int e=p[(i+j)%(n-4)];
							if(i==j) {
								ans[i][j]=a;
								ans[i][n-4]=ans[n-4][j]=e;
							} else if((j-i+n-4)%(n-4)==1) {
								ans[i][j]=b;
								ans[i][n-3]=ans[n-3][j]=e;
							} else if((j-i+n-4)%(n-4)==2) {
								ans[i][j]=c2;
								ans[i][n-2]=ans[n-2][j]=e;
							} else if((j-i+n-4)%(n-4)==3) {
								ans[i][j]=d;
								ans[i][n-1]=ans[n-1][j]=e;
							} else
								ans[i][j]=e;
						}
					}
				}
			}
		}
	}
	//bottom right 3x3
	if(n>5&&n%2==0) {
		for(int a=1; a<=n; ++a) {
			for(int b=1; b<=n; ++b) {
				for(int c=1; c<=n; ++c) {
					if(b==a||c==a||b==c||(n-2)*a+b+c!=k)
						continue;
					vector<int> p;
					for(int i=1; i<=n; ++i)
						if(i^a&&i^b&&i^c)
							p.push_back(i);
					ans[n-3][n-3]=ans[n-2][n-1]=ans[n-1][n-2]=a;
					ans[n-3][n-2]=ans[n-2][n-3]=ans[n-1][n-1]=b;
					ans[n-3][n-1]=ans[n-2][n-2]=ans[n-1][n-3]=c;
					for(int i=0; i<n-3; ++i) {
						for(int j=0; j<n-3; ++j) {
							int e=p[(i+j)%(n-3)];
							if(i==j) {
								ans[i][j]=a;
								ans[i][n-3]=ans[n-3][j]=e;
							} else if((j-i+n-3)%(n-3)==1) {
								ans[i][j]=b;
								ans[i][n-2]=ans[n-2][j]=e;
							} else if((j-i+n-3)%(n-3)==2) {
								ans[i][j]=c;
								ans[i][n-1]=ans[n-1][j]=e;
							} else
								ans[i][j]=e;
						}
					}
				}
			}
		}
	}
	bool b1=n%2==0&&k==n*n-2;
	if(b1) {
		k=n+2;
	}
	if(n%2==0&&k==n+2) {
		for(int i=0; i<n/2; ++i) {
			for(int j=0; j<n/2; ++j) {
				int k=(j-i+n/2)%(n/2)*2+1;
				int v1=k, v2=k+1;
				if(i==n/2-1&&j==n/2-1)
					swap(v1, v2);
				ans[2*i][2*j]=ans[2*i+1][2*j+1]=v1;
				ans[2*i][2*j+1]=ans[2*i+1][2*j]=v2;
			}
		}
	}
	if(b1) {
		for(int i=0; i<n; ++i)
			for(int j=0; j<n; ++j)
				ans[i][j]=n+1-ans[i][j];
		k=n*n-2;
	}
	if(n==7) {
		bool b2=k==46;
		if(b2)
			k=10;
		if(k==10) {
			string s="2315746413756276243155461237625317415764233742651";
			for(int i=0; i<n; ++i)
				for(int j=0; j<n; ++j)
					ans[i][j]=s[i*n+j]-'0';
		}
		if(b2) {
			for(int i=0; i<n; ++i)
				for(int j=0; j<n; ++j)
					ans[i][j]=n+1-ans[i][j];
			k=46;
		}
	}
	if(!ans[0][0]) {
		cout << "IMPOSSIBLE\n";
		return;
	}
	cout << "POSSIBLE\n";
	for(int i=0; i<n; ++i) {
		for(int j=0; j<n; ++j)
			cout << ans[i][j] << " ";
		cout << "\n";
	}
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