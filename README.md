# Compilers HW1 - Part 1
Έχουμε ότι ** προηγείται του / .
Επομένως χωρίζουμε σε 3 κατηγορίες την έκφραση. 
- 1η κατηγορία : Exp (expression) και βάζουμε τον τελεστή με την χαμηλότερη προτεραιότητα δηλαδή (/).
- 2η κατηγορία : Term (όρος) και βάζουμε τον τελεστή με τη μεγαλύτερη προτεραιότητα δηλαδή **.
- 3η κατηγορία : Factor (παράγοντας) όπου θα έχουμε τα strings.
Πιο συγκεκριμένα, μία έκφραση term θα αποτελείται από όρους (term) που χωρίζοντα με /. Έπειτα, ένας όρος (term) θα αποτελείται από factors που χωρίζονται με **.

**ΓΙΑ ΤΟΝ ΤΕΛΕΣΤΗ /**<br>
Η εκφώνηση λέει ότι ο / είναι δεξιά προσεταιριστικός (δηλαδή το α/b/c είναι a/(b/c)). Η μορφή αυτού του κανόνα είναι:
 Expr -> Term/ Expr | Term
Όμως, σε έναν LL(1) parser, αν δύο επιλογές ξεκινάνε με το ίδιο σύμβολο (εδώ το Term), ο parser δεν ξέρει ποια να διαλέξει. Γι' αυτό κάνουμε  Αριστερή Παραγοντοποίηση (Left Factoring). Βγάζουμε το κοινό μέρος κοινό παράγοντα:
 Expr -> Term ExprTail
 ExprTail -> / Expr | ε 

**ΓΙΑ ΤΟΝ ΤΕΛΕΣΤΗ ** **<br>
Η εκφώνηση λέει ότι ο ** είναι αριστερά προσεταιριστικός (το a**b**c είναι (a**b)**c). Η μορφή του κανόνα είνα :
Term -> Term ** Factor | Factor
Ένας top-down parser θα έμπαινε σε άπειρη λούπα προσπαθώντας να το λύσει.
Εφαρμόζουμε τον τύπο Απαλοιφής Αριστερής Αναδρομής που είδαμε στη θεωρία:
Term -> Factor TermTail
termTail -> ** Factor TermTail | ε

**STRINGS / FACTORS**<br>
Τέλος, πρέπει να ορίσουμε τι είναι το Factor. Ένα Factor είναι είτε μια ολόκληρη έκφραση μέσα σε παρενθέσεις, είτε ένα απλό string:
 Factor -> (Expr) | String 
 Το string είναι απλώς μία ακολουθία από γράμματα. Για να είναι LL(1) θα εχουμε :
  String -> Char StringTail
  StringTail -> Char StringTail | ε
  CHar -> a...z | A...Z
  
  Τελικά η γρμματική μας θα είναι:
  Expr -> Term ExprTail
  ExprTail-> / Expr | ε (ε το κενό συνεπώς δεν υπαρχει συνέχεια)
  Term -> Factor TermTail
  TermTail-> ** Factor TermTail | ε
  Factor -> (expr) | String 
  String-> Char StringTail 
  StringTail -> Char StringTail | ε
  Char -> a....z | A... Z

  Χρησιμοπούμε την παραπανω γραμματική για να κάνουμε τα σύνολα first και follow <br>
  **FIRST:**<br>
  FIRST(Char) = {char}
  FIRST(StringTaiil)= {Char | ε} (παραγει char ή το κενό ε)
  FIRST(String)= {Char} (Το string ξεκινάει υποχρεωτικά με char)
  FIRST(Factor) = {(,char} (ξεκινάει με παρένθεση ή String)
  FIrST(TermTail)= {**, ε}
  FIRST(Term)= {(,char} (επειδή ξεκινάει με factor, θα παίρνει το First του Factor)
  First(ExprTail)= {/, ε}
  First(Expr)= {(, char)} (επειδή ξεκιναει με Term θα πάιρνει το First του Term)

  **FOLLOW :** <br>
  FOLLOW(Expr) = {$, ) ) (Περιέχει το $ γιατί είναι το αρχικό σύμβολο. Περιέχει το ) επειδή έχουμετ   τον κανόνα $Factor \rightarrow ( Expr )$).
  FOLLOW(ExprTail)= { $, ) } (Εμφανίζεται στο τέλος του κανόνα Expr-> Term \ ExprTail και             παίρνει οτι έχει το FOLLOW του Expr).
  FOLLOW(Term)=  { /, $ , ) } (Ακολουθείται από το ExprTail. Άρα παίρνει το FIRST του ExprTail        χωρίς το ε, δηλαδή το /, και το FOLLOW του Expr).
  FOLLOW(TermTail) = { /, $ , ) } (Εμφανίζεται στο τέλος του κανόνα Term -> Factor TermTail άρα       παίρνει το FOLLOW του Term).
  FOLLOW(Factor)= { **, /, $ ) } (Ακολουθείται από το TermTail. Παίρνει το FIRST του TermTail χωρίς   το ε, δηλαδή το ** και το FOLLOW του Term).
  FOLLLOW(String) = { **, /, $ ) } (Είναι στο τέλος του Factor -> String επομενως παίρνει το FOLLOW   του Factor).
  FOLLOW(StringTail) = { **, /, $ ) } (Είναι στο τέλος του String->  Char StringStringTail συνεπώς    παίρνει το follow του string
  FOLLOW(Char)={ char,**, /, $ ) } Ακολουθείται από το StringTail. Παίρνει το FIRST του StringTail    χωρις το κενο ε. Δηλαδή το char και to follow του StringTail
