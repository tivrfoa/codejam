#pragma GCC optimize("Ofast")
#include <bits/stdc++.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/time.h>
#include <sys/resource.h>

using namespace std;

/*** START OF TEMPLATE CODE ***/

typedef vector<string> vs;
typedef long long ll;
typedef complex<double> pnt;
typedef vector<int> vi;
typedef vector<ll> vll;
typedef pair<int, int> pii;
typedef pair<ll, ll> pll;

#define RA(x) begin(x), end(x)
#define FE(i, x) for (auto i = begin(x); i != end(x); ++i)
#define SZ(x) ((ll) (x).size())

template<class T>
vector<T> splitstr(const string &s)
{
    istringstream in(s);
    vector<T> out;
    copy(istream_iterator<T>(in), istream_iterator<T>(), back_inserter(out));
    return out;
}

static void solve_case(int cas);

int main(int argc, char * const *argv)
{
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    struct rlimit stack_limit;
    getrlimit(RLIMIT_STACK, &stack_limit);
    stack_limit.rlim_cur = 1024 * 1024 * 1024;
    setrlimit(RLIMIT_STACK, &stack_limit);

    if (argc >= 2)
    {
        int fd = open(argv[1], O_RDONLY);
        if (fd == -1) { perror(argv[1]); exit(1); }
        if (-1 == dup2(fd, 0)) { perror(argv[1]); exit(1); }
        if (-1 == close(fd)) { perror(argv[1]); exit(1); }
    }
    if (argc >= 3)
    {
        int fd = open(argv[2], O_WRONLY | O_CREAT, 0666);
        if (fd == -1) { perror(argv[2]); exit(1); }
        if (-1 == dup2(fd, 1)) { perror(argv[2]); exit(1); }
        if (-1 == close(fd)) { perror(argv[2]); exit(1); }
    }
    cin.exceptions(ios::failbit | ios::badbit);

    int cases;
    cin >> cases;
    for (int cas = 1; cas <= cases; cas++)
        solve_case(cas);
    return 0;
}

/*** END OF TEMPLATE CODE ***/

// Undefined sign for negative inputs
template<typename T> static constexpr T gcd(T a, T b) { return b ? gcd(b, a % b) : a; }
template<typename T> static constexpr T wrap_pos(T a, T m) { return a < 0 ? a + m : a; }
template<typename T> static constexpr T sqr(T a) { return a * a; }
// m must be positive
template<typename T> static constexpr T mod(T a, T m) { return wrap_pos(a % m, m); }

template<typename T>
static constexpr T inverse2(T a, T m) { return a <= 1 ? a : mod((1 - inverse2(m % a, a) * m) / a, m); }

// a must be relatively prime to m, m > 0
template<typename T>
static constexpr T inverse(T a, T m) { return inverse2(mod(a, m), m); }

template<typename T, typename P>
static constexpr T power(T a, P b) { return b == 0 ? T(1) : (b % 2) ? power(a, b - 1) * a : sqr(power(a, b / 2)); }

template<typename T, typename C, T Modulus>
class MR
{
private:
    struct tag_plus {}; // indicates value is in range [0, 2 * Modulus)
    struct tag_minus {}; // indicates value is in range (-Modulus, Modulus)
    struct tag_good {}; // indicates value is in range

    T value;

    static_assert(std::numeric_limits<C>::max() / Modulus / Modulus > 0, "compute type is too small");
    static_assert(Modulus < std::numeric_limits<T>::max() / 2, "storage type is too small");

    static constexpr T reduce(T value, tag_plus)
    {
        return value >= Modulus ? value - Modulus : value;
    }

    static constexpr T reduce(T value, tag_minus)
    {
        return value < 0 ? value + Modulus : value;
    }

    static constexpr T reduce(T value, tag_good) { return value; }

public:
    typedef T value_type;
    typedef C compute_type;
    static constexpr T modulus = Modulus;

    constexpr MR() : value(0) {}
    constexpr MR(C value) : value(reduce(value % Modulus, tag_minus())) {}
    template<typename tag_t>
    constexpr MR(T value, tag_t tag) : value(reduce(value, tag)) {}

    MR &operator=(C value) { this->value = reduce(value % Modulus, tag_minus()); return *this; }

    constexpr MR operator +(MR b) const { return MR(value + b.value, tag_plus()); }
    constexpr MR operator -(MR b) const { return MR(value - b.value, tag_minus()); }
    constexpr MR operator *(MR b) const { return MR(C(value) * C(b.value) % Modulus, tag_good()); }
    constexpr MR operator -() const { return MR(-value, tag_minus()); }

    MR &operator +=(MR b) { value = reduce(value + b.value, tag_plus()); return *this; }
    MR &operator -=(MR b) { value = reduce(value - b.value, tag_minus()); return *this; }
    MR &operator *=(MR b) { value = C(value) * C(b.value) % Modulus; return *this; }

    constexpr bool operator==(MR b) const { return value == b.value; }
    constexpr bool operator!=(MR b) const { return value != b.value; }

    constexpr T get() const { return value; }

    // These are only valid if the dividend is relatively prime to the modulus
    constexpr MR inverse() const
    {
        return MR(::inverse(C(value), C(Modulus)), tag_good());
    }
    constexpr MR operator /(MR b) const { return *this * b.inverse(); }
    MR &operator /=(MR b) { return *this *= b.inverse(); }
};

template<typename T, typename C, T Modulus>
static inline std::ostream &operator<<(std::ostream &o, MR<T, C, Modulus> mr)
{
    return o << mr.get();
}

typedef MR<int, ll, 1000000007> mr;

enum state
{
    START,
    PATH,
    FINITE,
    INFIN
};

static void recurse(vector<state> &st, vector<mr> &makes, const vector<vi> &edges, int cur)
{
    if (st[cur] == PATH)
    {
        st[cur] = INFIN;
        return;
    }
    else if (st[cur] != START)
        return;

    st[cur] = PATH;
    mr tot = 0;
    for (int v : edges[cur])
    {
        recurse(st, makes, edges, v);
        if (st[v] == INFIN)
        {
            st[cur] = INFIN;
            return;
        }
        tot += makes[v];
    }
    makes[cur] = tot;
    st[cur] = FINITE;
}

static void solve_case(int cas)
{
    int M;
    cin >> M;
    vector<vi> edges(M);
    vector<vi> redges(M);
    for (int i = 0; i < M; i++)
    {
        int a, b;
        cin >> a >> b;
        a--;
        b--;
        edges[i].push_back(a);
        edges[i].push_back(b);
        redges[a].push_back(i);
        redges[b].push_back(i);
    }

    vector<bool> useful(M);
    useful[0] = true;
    queue<int> q;
    q.push(0);
    while (!q.empty())
    {
        int c = q.front();
        q.pop();
        for (int v : redges[c])
            if (!useful[v])
            {
                useful[v] = true;
                q.push(v);
            }
    }

    vector<vi> edges2(M);
    for (int i = 0; i < M; i++)
    {
        if (useful[i])
        {
            for (int v : edges[i])
                if (useful[v])
                    edges2[i].push_back(v);
            if (SZ(edges2[i]) == 1 && edges2[i][0] == i)
                edges2[i].clear();
        }
    }
    edges = move(edges2);

    cout << "Case #" << cas << ": ";

    vector<state> st(M, START);
    vector<mr> makes(M);
    vi have(M);
    int any = false;
    for (int i = 0; i < M; i++)
    {
        cin >> have[i];
        if (have[i] && useful[i])
            any = true;
    }
    if (!any)
    {
        cout << "0\n";
        return;
    }

    int tail = 0;
    while (SZ(edges[tail]) == 1)
    {
        tail = edges[tail][0];
        if (tail == 0)
            break;
    }
    if (SZ(edges[tail]) >= 2)
    {
        cout << "UNBOUNDED\n";
        return;
    }
    edges[0].clear();
    st[0] = FINITE;
    makes[0] = 1;
    mr total = 0;

    for (int i = 0; i < M; i++)
        if (useful[i] && have[i])
        {
            recurse(st, makes, edges, i);
            if (st[i] == INFIN)
            {
                cout << "UNBOUNDED\n";
                return;
            }
            total += makes[i] * have[i];
        }
    cout << total << '\n';
}
