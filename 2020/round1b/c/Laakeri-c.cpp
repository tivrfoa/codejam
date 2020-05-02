#include <bits/stdc++.h>
#define F first
#define S second
using namespace std;
typedef long long ll;
typedef long double ld;

void solve(){
  int r,s;
  cin>>r>>s;
  vector<int> t;
  for (int i=0;i<s;i++){
    for (int ii=0;ii<r;ii++){
      t.push_back(ii);
    }
  }
  vector<pair<int,int>> ans;
  while (1) {
    bool bad = false;
    for (int i=1;i<(int)t.size();i++){
      if (t[i]<t[i-1]) {
        bad = true;
        break;
      }
    }
    if (!bad) {
      break;
    }
    int a=0;
    bool f=false;
    for (int i=0;i<(int)t.size();i++){
      if (!f) {
        if (t[i] != t[0]) {
          f=true;
        }
      } else {
        if (t[i] != t[i-1]) {
          a=i;
          break;
        }
      }
    }
    assert(a>1&&t[a-1]!=t[0]&&(t[a-1]==t[0]+1 || (t[a-1]==0&&t[0]==r-1)));
    int b=0;
    for (int i=a;i+1<(int)t.size();i++){
      if (t[i] == t[0]&&t[i+1]==t[a-1]) {
        b=i;
        break;
      }
    }
    if (b==0) {
      assert(t[0]==r-1);
      for (int i=0;i<(int)t.size();i++){
        if (t[i] != t[0]) {
          a=i;
          break;
        }
      }
      b=(int)t.size()-1;
    }
    assert(b>=a);
    ans.push_back({a, b-a+1});
    vector<int> nt=t;
    for (int i=0;i<a;i++){
      nt[b-a+1+i]=t[i];
    }
    for (int i=0;i<b-a+1;i++){
      nt[i]=t[a+i];
    }
    t=nt;
    /*
    for (int i=0;i<(int)t.size();i++){
      cout<<t[i]<<" ";
    }
    cout<<endl;
    */
  }
  cout<<ans.size()<<'\n';
  for (auto tt : ans) {
    cout<<tt.F<<" "<<tt.S<<'\n';
  }
}

int main(){
  ios_base::sync_with_stdio(0);
  cin.tie(0);
  int tcs;
  cin>>tcs;
  for (int tc=1;tc<=tcs;tc++){
    cout<<"Case #"<<tc<<": ";
    solve();
  }
}