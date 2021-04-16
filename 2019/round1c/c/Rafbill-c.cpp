


 #include <stdio.h>
 #include <bits/stdc++.h>






using namespace std;





template<class T, size_t... I>
void print_tuple(ostream& s, T const& a, index_sequence<I...>){
  using swallow = int[];
  (void)swallow{0, (void(s << (I == 0? "" : ", ") << get<I>(a)), 0)...};
}

template<class T>
ostream& print_collection(ostream& s, T const& a);
template<class... A>
ostream& operator<<(ostream& s, tuple<A...> const& a);
template<class A, class B>
ostream& operator<<(ostream& s, pair<A, B> const& a);

template<class T, size_t I>
ostream& operator<<(ostream& s, array<T, I> const& a) { return print_collection(s, a); }
template<class T>
ostream& operator<<(ostream& s, vector<T> const& a) { return print_collection(s, a); }
template<class T, class U>
ostream& operator<<(ostream& s, multimap<T, U> const& a) { return print_collection(s, a); }
template<class T>
ostream& operator<<(ostream& s, multiset<T> const& a) { return print_collection(s, a); }
template<class T, class U>
ostream& operator<<(ostream& s, map<T, U> const& a) { return print_collection(s, a); }
template<class T>
ostream& operator<<(ostream& s, set<T> const& a) { return print_collection(s, a); }

template<class T>
ostream& print_collection(ostream& s, T const& a){
  s << '[';
  for(auto it = begin(a); it != end(a); ++it){
    s << *it;
    if(it != prev(end(a))) s << " ";
  }
  return s << ']';
}

template<class... A>
ostream& operator<<(ostream& s, tuple<A...> const& a){
  s << '(';
  print_tuple(s, a, index_sequence_for<A...>{});
  return s << ')';
}

template<class A, class B>
ostream& operator<<(ostream& s, pair<A, B> const& a){
  return s << "(" << get<0>(a) << ", " << get<1>(a) << ")";
}







using li = long long int;
using lu = long long unsigned;

using ld = long double;

using pii = tuple<li, li>;
using piii = tuple<li, li, li>;
using piiii = tuple<li, li, li, li>;
using vi = vector<li>;
using vii = vector<pii>;
using viii = vector<piii>;
using vvi = vector<vi>;
using vvii = vector<vii>;
using vviii = vector<viii>;

template<class T>
using min_queue = priority_queue<T, vector<T>, greater<T> >;
template<class T>
using max_queue = priority_queue<T>;

struct empty_t {};



int ilog2(int x){ return 31 - __builtin_clz(x); }

template <class T>
struct identity : std::unary_function <T, T> {
  T operator() (const T& x) const {return x;}
};

template<class T>
T& smin(T& x, T const& y) { x = min(x,y); return x; }

template <class T>
T& smax(T& x, T const& y) { x = max(x, y); return x; }


template<typename T>
T isqrt(T const&x){
  static_assert(is_integral<T>::value, "is_integral<T>::value");
  assert(x>=T(0));
  T ret = static_cast<T>(sqrtl(x));
  while(ret>0 && ret*ret>x) --ret;
  while(x-ret*ret>2*ret) ++ret;
  return ret;
}

template<typename T>
T icbrt(T const&x) {
  static_assert(is_integral<T>::value, "is_integral<T>::value");
  assert(x>=T(0));
  T ret = static_cast<T>(cbrt(x));
  while(ret>0 && ret*ret*ret>x) --ret;
  while(x-ret*ret*ret>3*ret*(ret+1)) ++ret;
  return ret;
}

const int N = 16;
char A[N][N];
int dp[N][N][N][N];
int S[N+1][N+1];

int range(int x1, int x2, int y1, int y2) {
  return S[x2+1][y2+1] - S[x2+1][y1] - S[x1][y2+1] + S[x1][y1];
}

void solve(){
  int n,m; cin>>n>>m;
  for(li i = 0; i < (li)(n); ++i) for(li j = 0; j < (li)(m); ++j) {
     cin>>A[i][j];
  }




  for(li i = 0; i < (li)(n); ++i) for(li j = 0; j < (li)(m); ++j) {
    S[i+1][j+1] = (A[i][j] == '#') + S[i+1][j] + S[i][j+1] - S[i][j];
  }

  for(li sx = (1); sx <= (li)(n); ++sx) for(li sy = (1); sy <= (li)(m); ++sy) for(li x = 0; x < (li)(n+1-sx); ++x) for(li y = 0; y < (li)(m+1-sy); ++y) {
    set<int> C;
    int nw = 0;
    for(li i = (x); i <= (li)(x+sx-1); ++i) for(li j = (y); j <= (li)(y+sy-1); ++j) {
      if(range(i,i,y,y+sy-1) == 0) {
        int sx1 = i-x;
        int sx2 = x+sx-1-i;
        int d = dp[sx1][sy][x][y] ^ dp[sx2][sy][i+1][y];
        C.insert(d);
        if(d == 0) nw++;
      }
      if(range(x,x+sx-1,j,j) == 0) {
        int sy1 = j-y;
        int sy2 = y+sy-1-j;
        int d = dp[sx][sy1][x][y] ^ dp[sx][sy2][x][j+1];
        C.insert(d);
        if(d == 0) nw++;
      }
    }
    dp[sx][sy][x][y] = 0;
    while(C.count(dp[sx][sy][x][y])) dp[sx][sy][x][y]++;
    if(sx==n&&sy==m) cout << nw << endl;
  }
}

int main(){
  ios::sync_with_stdio(0); cin.tie(0);
  int n; cin>>n;
  for(li i = 0; i < (li)(n); ++i) {
    cout << "Case #" << (i+1) << ": ";
    solve();
  }
  return 0;
}
