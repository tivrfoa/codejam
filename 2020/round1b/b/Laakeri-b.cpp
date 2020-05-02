#include <bits/stdc++.h>
#define F first
#define S second
#define X real()
#define Y imag()
using namespace std;
typedef long long ll;
typedef long double ld;
typedef complex<ld> co;

pair<co, ld> md2(vector<co> R) {
  if (R.size()==0) {
    return {{0, 0}, -1};
  } else if (R.size()==1) {
    return {R[0], 0};
  } else if (R.size()==2) {
    return {(R[0]+R[1])/(ld)2.0, hypot(R[0].X-R[1].X, R[0].Y-R[1].Y)/2.0};
  } else {
    ld s=(co(0, 1)*(R[0]-R[2])*conj(R[2]-R[1])).Y/((R[0]-R[1])*conj(R[2]-R[1])).Y;
    co c=(R[0]+R[1])/(ld)2.0+co(0, 1)*s*((R[0]-R[1])/(ld)2.0);
    return {c, hypot(R[0].X-c.X, R[0].Y-c.Y)};
  }
}
pair<co, ld> md(vector<co>& P, int i, vector<co> R) {
  if (i==(int)P.size()||R.size()==3) {
    return md2(R);
  } else {
    auto D=md(P, i+1, R);
    if (hypot(P[i].X-D.F.X, P[i].Y-D.F.Y)>D.S) {
      R.push_back(P[i]);
      D=md(P, i+1, R);
    }
    return D;
  }
}
pair<co, ld> minEnclosing(vector<co> P) {
  random_shuffle(P.begin(), P.end());
  return md(P, 0, vector<co>());
}

mt19937 gen(1337);

ll C = 1e9;

ll getrand(ll a, ll b) {
  return uniform_int_distribution<ll>(a,b)(gen);
}

ll rA,rB;

int qs = 0;

string go(ll x, ll y) {
  qs++;
  cout<<x<<" "<<y<<endl;
  string s;
  cin>>s;
  assert(s=="MISS" || s=="HIT" || s=="CENTER");
  return s;
}

void solve(){
  qs=0;
  vector<pair<ll,ll>> hits;
  ll cx,cy;
  while (1) {
    cx = getrand(-C, C);
    cy = getrand(-C, C);
    string t = go(cx, cy);
    if (t=="CENTER") return;
    if (t=="HIT") {
      break;
    }
  }
  ll mi=cx;
  ll ma=C;
  while (mi<=ma){
    ll mid=(mi+ma)/2;
    string t = go(mid, cy);
    if (t=="CENTER") return;
    if (t=="HIT") {
      hits.push_back({mid, cy});
      mi=mid+1;
    } else {
      ma=mid-1;
    }
  }

  mi=-C;
  ma=cx;
  while (mi<=ma){
    ll mid=(mi+ma)/2;
    string t = go(mid, cy);
    if (t=="CENTER") return;
    if (t=="HIT") {
      hits.push_back({mid, cy});
      ma=mid-1;
    } else {
      mi=mid+1;
    }
  }

  mi=cy;
  ma=C;
  while (mi<=ma){
    ll mid=(mi+ma)/2;
    string t = go(cx, mid);
    if (t=="CENTER") return;
    if (t=="HIT") {
      hits.push_back({cx, mid});
      mi=mid+1;
    } else {
      ma=mid-1;
    }
  }

  mi=-C;
  ma=cy;
  while (mi<=ma){
    ll mid=(mi+ma)/2;
    string t = go(cx, mid);
    if (t=="CENTER") return;
    if (t=="HIT") {
      hits.push_back({cx, mid});
      ma=mid-1;
    } else {
      mi=mid+1;
    }
  }

  vector<co> hss;
  assert(hits.size()>=4);
  for (auto h : hits) {
    hss.push_back({(ld)h.F, (ld)h.S});
  }
  auto xd = minEnclosing(hss);
  cerr<<"got "<<xd.S<<" "<<xd.F<<endl;
  cx = (ll)xd.F.X;
  cy = (ll)xd.F.Y;
  assert(qs < 170);
  for (ll x=cx-5;x<=cx+5;x++){
    for (ll y=cy-5;y<=cy+5;y++){
      string t=go(x,y);
      if (t=="CENTER"){
        return;
      }
    }
  }
  assert(0);
}

int main(){
  ios_base::sync_with_stdio(0);
  cin.tie(0);
  int tcs;
  cin>>tcs>>rA>>rB;
  for (int tc=1;tc<=tcs;tc++){
    solve();
  }
}