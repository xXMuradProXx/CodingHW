public abstract class ExerciseObject {

    public abstract boolean isSmaller(ExerciseObject other);
    public abstract boolean isLarger(ExerciseObject other);
    public abstract boolean isEqual(ExerciseObject other);

    public boolean isSmallerOrEqual(ExerciseObject other){
        return isSmaller(other) || isEqual(other);
    };

    public boolean isLargerOrEqual(ExerciseObject other){
        return isLarger(other) || isEqual(other);
    };
}
